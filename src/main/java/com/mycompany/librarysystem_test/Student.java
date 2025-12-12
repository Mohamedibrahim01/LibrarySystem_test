package com.mycompany.librarysystem_test;

import javax.swing.JOptionPane;

public class Student implements Observer {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        // رسالة تظهر على الشاشة عشان الدكتور يشوف النتيجة بوضوح
        JOptionPane.showMessageDialog(null, "Notification for " + name + ":\n" + message);
    }
}