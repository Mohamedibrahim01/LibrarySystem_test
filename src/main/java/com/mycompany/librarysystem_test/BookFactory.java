package com.mycompany.librarysystem_test;

public class BookFactory {
    public static Book createBook(String type, String title, String author) {
        // إنشاء الكتاب ببساطة
        return new Book(title, author, type);
    }
}