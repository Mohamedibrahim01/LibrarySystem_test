package com.mycompany.librarysystem_test;

public abstract class BookDecorator extends Book {
    protected Book decoratedBook;

    public BookDecorator(Book book) {
        // لازم ننادي الـ super عشان الوراثة تمشي صح
        super(book.getTitle(), book.getAuthor(), book.getType());
        this.decoratedBook = book;
    }

    // توجيه كل الدوال للكتاب الأصلي (Delegation)
    @Override
    public String getTitle() { return decoratedBook.getTitle(); }
    
    @Override
    public String getAuthor() { return decoratedBook.getAuthor(); }
    
    @Override
    public String getType() { return decoratedBook.getType(); }
    
    @Override
    public boolean isBorrowed() { return decoratedBook.isBorrowed(); }
    
    @Override
    public void setBorrowed(boolean borrowed) { decoratedBook.setBorrowed(borrowed); }
    
    @Override
    public void returnBook() { decoratedBook.returnBook(); }
    
    @Override
    public void addObserver(Observer o) { decoratedBook.addObserver(o); }
}