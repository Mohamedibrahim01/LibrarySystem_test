/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.factory;

import com.mycompany.library.model.Magazine;
import com.mycompany.library.model.LibraryItem;
import com.mycompany.library.model.Book;


/**
 *
 * @author Hassan
 */
public class LibraryItemFactory {
 public static LibraryItem createItem(String title, String type) {
        if (type.equalsIgnoreCase("Book")) {
            return new Book(title);
        } else if (type.equalsIgnoreCase("Magazine")) {
            return new Magazine(title);
        }
        return new LibraryItem(title, "General");
    }
}
