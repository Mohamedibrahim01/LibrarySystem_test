 
package com.mycompany.library.patterns.strategy;

import java.util.List;
import com.mycompany.library.model.LibraryItem;


public interface SearchStrategy {
 List<LibraryItem> search(List<LibraryItem> items, String query);   
}
