package model;

import java.util.ArrayList;

public abstract class AbstractSorter {
    public ArrayList<Integer> l;

    public AbstractSorter(ArrayList<Integer> l) {
        this.l = l;
    }

    public abstract void sort();
}
