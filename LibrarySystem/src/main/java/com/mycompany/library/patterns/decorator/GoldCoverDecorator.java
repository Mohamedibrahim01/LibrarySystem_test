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
public class GoldCoverDecorator extends ItemDecorator {

    public GoldCoverDecorator(LibraryItem item) {
        super(item);
    }

    @Override
    public String toString() {
        // هنا نضيف الميزة الإضافية على النص الأصلي
        return decoratedItem.toString() + " ✨ [Gold Edition]";
    }
}