/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.decorator;

import com.mycompany.library.model.LibraryItem;



/**
 *
 * @author Hassan
 */
public abstract class ItemDecorator extends LibraryItem {
    protected LibraryItem decoratedItem;

    public ItemDecorator(LibraryItem item) {
        // الحل للمشكلة: يجب تمرير بيانات العنصر الأصلي للأب (LibraryItem)
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
