
package com.mycompany.library.patterns.observer;

import java.util.ArrayList;
import java.util.List;


public class BookAvailabilitySubject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String bookName) {
        for (Observer observer : observers) {
            observer.update("The book " + bookName + " is now available!");
        }
    }
}