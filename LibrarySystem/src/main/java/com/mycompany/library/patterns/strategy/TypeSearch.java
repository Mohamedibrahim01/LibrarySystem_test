 
package com.mycompany.library.patterns.strategy;

import java.util.List;
import com.mycompany.library.model.LibraryItem;
import java.util.ArrayList;

public class TypeSearch implements SearchStrategy {
    @Override
    public List<LibraryItem> search(List<LibraryItem> items, String query) {
     
     List<LibraryItem> newList = new ArrayList<>();
     
     for(LibraryItem i : items){
         String type = i.getType().toLowerCase();
         
         if(type.contains(query.toLowerCase())){
             newList.add(i);
         }
     }
     
     return newList;
    }
}