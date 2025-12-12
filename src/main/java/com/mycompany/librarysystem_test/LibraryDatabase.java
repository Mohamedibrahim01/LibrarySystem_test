package com.mycompany.librarysystem_test;

import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {
    
    private static LibraryDatabase instance;
    private List<Book> books;

    private LibraryDatabase() {
        books = new ArrayList<>();
    }

    public static synchronized LibraryDatabase getInstance() {
        if (instance == null) {
            instance = new LibraryDatabase();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
    }
    
    // بنرجع الليست مباشرة عشان نستخدم Loop عادية في الـ Main
    public List<Book> getBooks() {
        return books;
    }
}