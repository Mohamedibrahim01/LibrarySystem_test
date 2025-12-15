package com.mycompany.library;

import com.mycompany.library.model.LibraryItem;
import com.mycompany.library.patterns.decorator.GoldCoverDecorator;
import com.mycompany.library.patterns.factory.LibraryItemFactory;
import com.mycompany.library.patterns.singleton.LibraryDatabase;
import com.mycompany.library.patterns.strategy.SearchStrategy;
import com.mycompany.library.patterns.strategy.TitleSearch;
import com.mycompany.library.patterns.strategy.TypeSearch;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

// تأكد أن اسم الكلاس هنا يطابق اسم الملف (PrimaryController بحرف P كبير أفضل)
public class primaryController implements Initializable {

    @FXML private TextField txtTitle;
    @FXML private ComboBox<String> cmbType;
    @FXML private CheckBox chkGoldCover;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. إعداد القوائم الأساسية
        db.addItem(LibraryItemFactory.createItem("Clean Code", "Book"));
        db.addItem(LibraryItemFactory.createItem("Introduction to Algorithms (CLRS)", "Book"));
        db.addItem(LibraryItemFactory.createItem("Head First Design Patterns", "Book"));
        db.addItem(LibraryItemFactory.createItem("Harry Potter and the Philosopher's Stone", "Book"));
        
        // كتب ذهبية (Gold Edition) - عشان نجرب الديكوريتور
        LibraryItem goldBook = LibraryItemFactory.createItem("The Lord of the Rings", "Book");
        db.addItem(new GoldCoverDecorator(goldBook)); // ده هيظهر لونه ذهبي

        // 2. مجلات (Magazines)
        db.addItem(LibraryItemFactory.createItem("National Geographic: Space Issue", "Magazine"));
        db.addItem(LibraryItemFactory.createItem("PC Gamer: Best GPUs 2025", "Magazine"));
        db.addItem(LibraryItemFactory.createItem("Forbes: Top Tech Companies", "Magazine"));

        // 3. دوريات علمية (Journals)
        db.addItem(LibraryItemFactory.createItem("IEEE Transactions on Software Engineering", "Journal"));
        db.addItem(LibraryItemFactory.createItem("Nature: Climate Change Report", "Journal"));

        // 4. خرائط رقمية (Digital Maps)
        db.addItem(LibraryItemFactory.createItem("Google Maps Offline: Cairo", "Digital Map"));
        db.addItem(LibraryItemFactory.createItem("World Atlas 2025", "Digital Map"));
        cmbType.getItems().addAll("Book", "Magazine");
        cmbType.getSelectionModel().selectFirst();
        listViewItems.setItems(db.getInventory());
        
        // 2. إعداد استراتيجيات البحث
        cmbSearchStrategy.getItems().addAll("By Title", "By Type");
        cmbSearchStrategy.getSelectionModel().select("By Title");
        currentSearchStrategy = new TitleSearch(); 

        cmbSearchStrategy.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal.equals("By Title")) currentSearchStrategy = new TitleSearch();
                else if (newVal.equals("By Type")) currentSearchStrategy = new TypeSearch();
                performSearch(txtSearch.getText());
            }
        });

        txtSearch.textProperty().addListener((obs, oldText, newText) -> performSearch(newText));

        // 3. (تحديث هام) Cell Factory: ألوان + كليك يمين للإرجاع
        listViewItems.setCellFactory(param -> new ListCell<LibraryItem>() {
            @Override
            protected void updateItem(LibraryItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    setContextMenu(null); // إلغاء القائمة للخلايا الفارغة
                } else {
                    setText(item.toString());

                    // --- منطق الألوان (Decorators) ---
                    if (item instanceof GoldCoverDecorator) {
                        setStyle("-fx-text-fill: #FFD700; -fx-font-weight: bold;"); 
                    } else {
                        // لو الكتاب مستعار، نلونه أحمر خفيف لتمييزه، وإلا أبيض
                        if (!item.isAvailable()) {
                            setStyle("-fx-text-fill: #E74C3C;"); // لون أحمر للمستعار
                        } else {
                            setStyle("-fx-text-fill: #EEEEEE;");
                        }
                    }

                    // --- منطق الإرجاع (Right Click Context Menu) ---
                    // القائمة تظهر فقط لو الكتاب "غير متاح" (يعني مستعار)
                    if (!item.isAvailable()) {
                        ContextMenu menu = new ContextMenu();
                        MenuItem returnItem = new MenuItem("🔄 Return this Item");
                        
                        returnItem.setOnAction(event -> {
                            // 1. تغيير الحالة
                            item.setAvailable(true);
                            SoundManager.playReturn();
                            // 2. تحديث الإحصائيات والقوائم
                            updateStats();
                            refreshBorrowList();
                            listViewItems.refresh(); // لإعادة الرسم وتغيير اللون
                            
                            logNotification("Item Returned: " + item.getTitle());
                        });
                        
                        menu.getItems().add(returnItem);
                        setContextMenu(menu);
                    } else {
                        setContextMenu(null); // لو متاح، مفيش قائمة إرجاع
                    }
                }
            }
        });

        
        // تصفير العدادات عند البدء
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

        // إنشاء العنصر
        LibraryItem newItem = LibraryItemFactory.createItem(title, type);
        
        // تطبيق الـ Decorator فعلياً (تعديل مهم)
        if (chkGoldCover.isSelected()) {
            newItem = new GoldCoverDecorator(newItem); 
        }

        // الإضافة للداتابيز
        db.addItem(newItem);
        updateStats();
        SoundManager.playSuccess();
        // تحديث الواجهات
        refreshBorrowList();
        txtSearch.clear(); // مسح البحث لضمان ظهور العنصر الجديد
        
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
        selectedBook.setAvailable(false);
        updateStats();
        SoundManager.playSuccess();
        // تحديث الواجهات
        listViewItems.refresh();
        refreshBorrowList();
        txtSearch.clear(); // مسح البحث
        
        logNotification("Loan created for: " + user + " -> " + selectedBook.getTitle());
        
        cmbBorrowBook.getSelectionModel().clearSelection();
        txtUser.clear();
        dtDate.setValue(null);
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
    
    // دالة تنفيذ البحث
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

        // تحديث النصوص في البطاقات
        lblTotal.setText(String.valueOf(total));
        lblAvailable.setText(String.valueOf(available));
        lblBorrowed.setText(String.valueOf(borrowed));
    }
  
}