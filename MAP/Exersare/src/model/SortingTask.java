package model;

public class SortingTask extends Task {
    private Integer [] arr;
    private AbstractSorter sorter;

    public SortingTask(String taskId, String descriere,Integer[] arr) {
        super(taskId,descriere);
        this.arr = arr;
        this.sorter=new MergeSort(); // ???
    }

    @Override
    public void execute() {
        sorter.sort(arr);
        for(Integer el:arr) System.out.println(el);
    }
}
