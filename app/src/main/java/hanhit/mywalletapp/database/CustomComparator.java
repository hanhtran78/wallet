package hanhit.mywalletapp.database;

import java.util.Comparator;

import hanhit.mywalletapp.model.Item;

/**
 * Created by HanhTv on 7/23/2016.
 */
public class CustomComparator implements Comparator<Item> {
    @Override
    public int compare(Item lhs, Item rhs) {
        return lhs.getDateItem().compareTo(rhs.getDateItem());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
