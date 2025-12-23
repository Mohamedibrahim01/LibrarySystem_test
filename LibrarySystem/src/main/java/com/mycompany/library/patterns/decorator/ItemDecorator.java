
package com.mycompany.library.patterns.decorator;

import com.mycompany.library.model.LibraryItem;




public abstract class ItemDecorator extends LibraryItem {
    protected LibraryItem decoratedItem;

    public ItemDecorator(LibraryItem item) {
       
        super(item.getTitle(), "Decorated"); 
        this.decoratedItem = item;
    }

    @Override
    public String toString() {
        return decoratedItem.toString();
    }
    
    @Override
    public boolean isAvailable() {
        return decoratedItem.isAvailable();
    }
    
    @Override
    public void setAvailable(boolean available) {
        decoratedItem.setAvailable(available);
    }
}
