/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.library.patterns.strategy;

import java.util.List;
import com.mycompany.library.model.LibraryItem;

/**
 *
 * @author Hassan
 */
public interface SearchStrategy {
 List<LibraryItem> search(List<LibraryItem> items, String query);   
}
