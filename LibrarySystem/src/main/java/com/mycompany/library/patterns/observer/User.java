/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.observer;

/**
 *
 * @author Hassan
 */
public class User implements Observer {
    private String name;
    public User(String name) { this.name = name; }
    
    @Override
    public void update(String message) {
        System.out.println("Notification for " + name + ": " + message);
    }
}