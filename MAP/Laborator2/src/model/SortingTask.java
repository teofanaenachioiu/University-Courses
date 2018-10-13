package model;

import java.util.ArrayList;

public class SortingTask extends Task {
    private ArrayList<Integer> arr;
    private AbstractSorter sorter;

    public SortingTask(String taskId, String descriere, ArrayList<Integer> arr) {
        super(taskId,descriere);
        this.arr = arr;
        this.sorter=new MergeSort();
    }

    @Override
    public void execute() {
        sorter.sort(arr);
        for(Integer el:arr) System.out.print(el+" ");
        System.out.println();
    }
}
