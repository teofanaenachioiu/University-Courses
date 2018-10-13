package model;

public class SortingTask {
    private AbstractSorter a;

    public SortingTask(AbstractSorter a) {
        this.a = a;
    }

    public void execute (){
        a.sort();
    }

}
