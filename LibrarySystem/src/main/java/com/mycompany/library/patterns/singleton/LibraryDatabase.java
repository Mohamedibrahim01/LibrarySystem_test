
package com.mycompany.library.patterns.singleton;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mycompany.library.model.LibraryItem;
import java.util.ArrayList;

// 1-  LibraryDatabase db = LibraryDatabase().getInstacne();




// 2- // LibraryDatabase db = LibraryDatabase().getInstacne();

public class LibraryDatabase {
   private static LibraryDatabase instance;
   private ObservableList<LibraryItem> inventory;

    private LibraryDatabase() {
        inventory = FXCollections.observableArrayList();
    }

    public static synchronized LibraryDatabase getInstance() {
        if (instance == null) {
            instance = new LibraryDatabase();
        }
        return instance;
    }

    public ObservableList<LibraryItem> getInventory() {
        return inventory;
    }

    public void addItem(LibraryItem item) {
        inventory.add(item);
    }

    public List<LibraryItem> getAvailableItems() {
//       return inventory.stream()
//                .filter(LibraryItem::isAvailable)
//                .collect(Collectors.toList());

    List<LibraryItem> items = new ArrayList<>();
        
        for(LibraryItem item : inventory){
            if(item.isAvailable())
                items.add(item);
        }
        return items;
    }
}
