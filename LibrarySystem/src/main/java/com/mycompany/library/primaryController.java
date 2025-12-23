package com.mycompany.library;

import com.mycompany.library.model.LibraryItem;
import com.mycompany.library.patterns.builder.LoanTicket;
import com.mycompany.library.patterns.decorator.GoldCoverDecorator;
import com.mycompany.library.patterns.factory.LibraryItemFactory;
import com.mycompany.library.patterns.observer.BookAvailabilitySubject;
import com.mycompany.library.patterns.observer.User; 
import com.mycompany.library.patterns.singleton.LibraryDatabase;
import com.mycompany.library.patterns.strategy.SearchStrategy;
import com.mycompany.library.patterns.strategy.TitleSearch;
import com.mycompany.library.patterns.strategy.TypeSearch;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

 public class primaryController implements Initializable {

    @FXML private TextField txtTitle;
    @FXML private ComboBox<String> cmbType;
    @FXML private CheckBox chkGoldCover;
    
    @FXML private CheckBox chkPremium; 
    
    @FXML private ComboBox<LibraryItem> cmbBorrowBook;
    @FXML private TextField txtUser;
    @FXML private DatePicker dtDate;
    @FXML private ListView<LibraryItem> listViewItems;
    @FXML private TextArea txtNotifications;
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbSearchStrategy;
    @FXML private Label lblTotal;
    @FXML private Label lblBorrowed;
    @FXML private Label lblAvailable;

    private LibraryDatabase db = LibraryDatabase.getInstance();
    private SearchStrategy currentSearchStrategy;
    private BookAvailabilitySubject notificationSystem = new BookAvailabilitySubject();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         db.addItem(LibraryItemFactory.createItem("Clean Code", "Book"));
        db.addItem(LibraryItemFactory.createItem("Introduction to Algorithms (CLRS)", "Book"));
        db.addItem(LibraryItemFactory.createItem("Head First Design Patterns", "Book"));
        db.addItem(LibraryItemFactory.createItem("Harry Potter and the Philosopher's Stone", "Book"));

        LibraryItem goldBook = LibraryItemFactory.createItem("The Lord of the Rings", "Book");
        db.addItem(new GoldCoverDecorator(goldBook));

        db.addItem(LibraryItemFactory.createItem("National Geographic: Space Issue", "Magazine"));
        db.addItem(LibraryItemFactory.createItem("PC Gamer: Best GPUs 2025", "Magazine"));
        db.addItem(LibraryItemFactory.createItem("Forbes: Top Tech Companies", "Magazine"));

        db.addItem(LibraryItemFactory.createItem("IEEE Transactions on Software Engineering", "Journal"));
        db.addItem(LibraryItemFactory.createItem("Nature: Climate Change Report", "Journal"));

        db.addItem(LibraryItemFactory.createItem("Google Maps Offline: Cairo", "Digital Map"));
        db.addItem(LibraryItemFactory.createItem("World Atlas 2025", "Digital Map"));
        
        cmbType.getItems().addAll("Book", "Magazine");
        cmbType.getSelectionModel().selectFirst();
        listViewItems.setItems(db.getInventory());

         cmbSearchStrategy.getItems().addAll("By Title", "By Type");
        cmbSearchStrategy.getSelectionModel().select("By Title");
        currentSearchStrategy = new TitleSearch();

        cmbSearchStrategy.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal.equals("By Title")) {
                    currentSearchStrategy = new TitleSearch();
                } else if (newVal.equals("By Type")) {
                    currentSearchStrategy = new TypeSearch();
                }
                performSearch(txtSearch.getText());
            }
        });

        txtSearch.textProperty().addListener((obs, oldText, newText) -> performSearch(newText));

       
        notificationSystem.attach(message -> {
             Platform.runLater(() -> {
                txtNotifications.appendText("🔔 NOTICE: " + message + "\n");
            });
        });
        
        notificationSystem.attach(new User("Ahmed"));
        notificationSystem.attach(new User("Sarah"));

         listViewItems.setCellFactory(param -> new ListCell<LibraryItem>() {
            @Override
            protected void updateItem(LibraryItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    setContextMenu(null);
                } else {
                    setText(item.toString());

                    if (item instanceof GoldCoverDecorator) {
                        setStyle("-fx-text-fill: #FFD700; -fx-font-weight: bold;");
                    } else {
                        if (!item.isAvailable()) {
                            setStyle("-fx-text-fill: #E74C3C;");  
                        } else {
                            setStyle("-fx-text-fill: #EEEEEE;");
                        }
                    }

                   
                    if (!item.isAvailable()) {
                        ContextMenu menu = new ContextMenu();
                        MenuItem returnItem = new MenuItem("🔄 Return this Item");

                        returnItem.setOnAction(event -> {
                            
                            item.setAvailable(true);
                            SoundManager.playReturn();
                            
                            
                            notificationSystem.notifyObservers(item.getTitle());

                             
                            updateStats();
                            refreshBorrowList();
                            listViewItems.refresh();
                        });

                        menu.getItems().add(returnItem);
                        setContextMenu(menu);
                    } else {
                        setContextMenu(null);
                    }
                }
            }
        });

        updateStats();
        refreshBorrowList();
        logNotification("System Ready.");
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        String title = txtTitle.getText();
        String type = cmbType.getValue();

        if (title.isEmpty()) {
            showAlert("Error", "Please enter title.");
            return;
        }

        LibraryItem newItem = LibraryItemFactory.createItem(title, type);

        if (chkGoldCover.isSelected()) {
            newItem = new GoldCoverDecorator(newItem);
        }

        db.addItem(newItem);
        updateStats();
        SoundManager.playSuccess();
        refreshBorrowList();
        txtSearch.clear();
        logNotification("Added: " + newItem.toString());
        txtTitle.clear();
        chkGoldCover.setSelected(false);
    }

    @FXML
    private void handleBorrowAction(ActionEvent event) {
        LibraryItem selectedBook = cmbBorrowBook.getValue();
        String user = txtUser.getText();

        if (selectedBook == null || user.isEmpty() || dtDate.getValue() == null) {
            showAlert("Error", "Select book and fill details.");
            return;
        }
        if (dtDate.getValue().isBefore(java.time.LocalDate.now())) {
            SoundManager.playError();
            showAlert("Error", "Date cannot be in the past!!");
            dtDate.setValue(null);
            return;
        }
        
         
        boolean isPremium = chkPremium.isSelected();
        
        LoanTicket ticket = new LoanTicket.Builder(user, selectedBook.getTitle())
                                .setDate(dtDate.getValue().toString())
                                .setPremium(isPremium)  
                                .build();
                                
        logNotification("🎫 Ticket Generated: " + user + " (Premium: " + isPremium + ")");
 
        selectedBook.setAvailable(false);
        updateStats();
        SoundManager.playSuccess();
        listViewItems.refresh();
        refreshBorrowList();
        txtSearch.clear();
        logNotification("Loan created for: " + user + " -> " + selectedBook.getTitle());

        cmbBorrowBook.getSelectionModel().clearSelection();
        txtUser.clear();
        dtDate.setValue(null);
        chkPremium.setSelected(false);  
    }

    private void refreshBorrowList() {
        cmbBorrowBook.getItems().clear();
        cmbBorrowBook.getItems().addAll(db.getAvailableItems());
    }

    private void logNotification(String msg) {
        txtNotifications.appendText(">> " + msg + "\n");
    }

    private void showAlert(String title, String content) {
        SoundManager.playError();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void performSearch(String query) {
        if (query == null || query.isEmpty()) {
            listViewItems.setItems(db.getInventory());
        } else {
            List<LibraryItem> results = currentSearchStrategy.search(db.getInventory(), query);
            listViewItems.setItems(FXCollections.observableArrayList(results));
        }
    }

    private void updateStats() {
        long total = db.getInventory().size();
        long available = db.getInventory().stream().filter(LibraryItem::isAvailable).count();
        long borrowed = total - available;

        lblTotal.setText(String.valueOf(total));
        lblAvailable.setText(String.valueOf(available));
        lblBorrowed.setText(String.valueOf(borrowed));
    }
}