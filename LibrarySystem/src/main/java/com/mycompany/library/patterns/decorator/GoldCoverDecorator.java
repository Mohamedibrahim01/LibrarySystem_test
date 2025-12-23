
package com.mycompany.library.patterns.decorator;

import com.mycompany.library.model.LibraryItem;


public class GoldCoverDecorator extends ItemDecorator {

    public GoldCoverDecorator(LibraryItem item) {
        super(item);
    }

    @Override
    public String toString() {
        return decoratedItem.toString() + " ✨ [Gold Edition]";
    }
}