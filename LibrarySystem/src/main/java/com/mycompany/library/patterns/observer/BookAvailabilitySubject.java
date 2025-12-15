/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hassan
 */
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