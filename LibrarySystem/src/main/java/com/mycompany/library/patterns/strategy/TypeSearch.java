/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.patterns.strategy;

import java.util.List;
import java.util.stream.Collectors;
import com.mycompany.library.model.LibraryItem;

/**
 *
 * @author Hassan
 */
public class TypeSearch implements SearchStrategy {
    @Override
    public List<LibraryItem> search(List<LibraryItem> items, String query) {
        return items.stream()
                .filter(item -> item.getType().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}