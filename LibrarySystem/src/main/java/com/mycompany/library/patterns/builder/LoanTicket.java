/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.builder;

/**
 *
 * @author Hassan
 */
public class LoanTicket {
    private String userName;
    private String bookTitle;
    private String date;
    private boolean isPremium;
    
    private LoanTicket(Builder builder) {
        this.userName = builder.userName;
        this.bookTitle = builder.bookTitle;
        this.date = builder.date;
        this.isPremium = builder.isPremium;
    }
    
    public static class Builder {
        private String userName;
        private String bookTitle;
        private String date;
        private boolean isPremium;

        public Builder(String userName, String bookTitle) { // الحقول الإجبارية
            this.userName = userName;
            this.bookTitle = bookTitle;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setPremium(boolean isPremium) {
            this.isPremium = isPremium;
            return this;
        }

        public LoanTicket build() {
            return new LoanTicket(this);
        }
    }
}
