package com.mycompany.librarysystem_test;

import java.util.ArrayList;
import java.util.List;

public class Book {
    
    private String title;
    private String author;
    private String type;
    private boolean isBorrowed;
    
    // قائمة المراقبين (Observer Pattern)
    private List<Observer> observers = new ArrayList<>();

    // 1. كونستركتور للـ Builder
    public Book(Builder builder) {
        this.title = builder.getTitle();
        this.author = builder.getAuthor();
        this.type = builder.getType();
        this.isBorrowed = false;
    }

    // 2. كونستركتور عادي (للـ Factory والـ Decorator)
    public Book(String title, String author, String type) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.isBorrowed = false;
    }

    // --- Getters ---
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getType() { return type; }
    public boolean isBorrowed() { return isBorrowed; }

    public void setBorrowed(boolean borrowed) { 
        this.isBorrowed = borrowed; 
    }

    // --- Observer Methods ---
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void returnBook() {
        this.isBorrowed = false;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("Book Available: '" + this.title + "' is ready for pickup.");
        }
    }
}