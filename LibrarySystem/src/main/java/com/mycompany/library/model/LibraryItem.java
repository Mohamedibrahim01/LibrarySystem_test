
package com.mycompany.library.model;

 
public class LibraryItem {
    protected String title;
    protected String type;
    protected boolean isAvailable;

    public LibraryItem(String title, String type) {
        this.title = title;
        this.type = type;
        this.isAvailable = true;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        String status = isAvailable ? "[Available]" : "[BORROWED]";
        return String.format("%s - %s %s", type, title, status);
    }
}