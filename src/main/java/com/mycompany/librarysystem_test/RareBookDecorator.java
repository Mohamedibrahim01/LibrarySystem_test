package com.mycompany.librarysystem_test;

public class RareBookDecorator extends BookDecorator {

    public RareBookDecorator(Book book) {
        super(book);
    }

    @Override
    public String getTitle() {
        // الميزة الإضافية (تغيير اسم الكتاب)
        return super.getTitle() + " [RARE EDITION - Handle With Care]";
    }
}