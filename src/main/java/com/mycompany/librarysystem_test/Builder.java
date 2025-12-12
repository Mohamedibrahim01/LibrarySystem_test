package com.mycompany.librarysystem_test;

public class Builder {
    private String title;
    private String author;
    private String type;

    public Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Builder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Builder setType(String type) {
        this.type = type;
        return this;
    }

    // Getters for the Book class to use
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getType() { return type; }

    public Book build() {
        return new Book(this);
    }
}