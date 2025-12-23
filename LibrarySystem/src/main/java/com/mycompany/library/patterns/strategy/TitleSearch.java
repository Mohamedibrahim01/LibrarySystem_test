 
package com.mycompany.library.patterns.strategy;

import java.util.List;
import java.util.stream.Collectors;
import com.mycompany.library.model.LibraryItem;
import java.util.ArrayList;

public class TitleSearch implements SearchStrategy {

    @Override
    public List<LibraryItem> search(List<LibraryItem> items, String query) {

        List<LibraryItem> newList = new ArrayList<>();
        
        for (LibraryItem i : items) {
            
            String title = i.getTitle().toLowerCase();

            if (title.contains(query.toLowerCase())) {
                newList.add(i);
            }
        }
        return newList;

    }
}
