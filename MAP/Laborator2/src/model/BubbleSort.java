package model;

import java.util.ArrayList;
import java.util.Collections;
//ex 3
public class BubbleSort extends AbstractSorter {
    @Override
    public  void sort(ArrayList<Integer> arr) {
        int n = arr.size();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr.get(j-1) > arr.get(j)) {
                    Collections.swap(arr, j-1, j);
                }
            }
        }
    }
}
