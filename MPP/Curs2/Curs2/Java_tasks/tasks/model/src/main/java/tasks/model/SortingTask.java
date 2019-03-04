package tasks.model;

import tasks.repository.HasId;

/**
 * Created by grigo on 11/14/16.
 */
public class SortingTask extends Task implements HasId<Integer> {
    private int id, nrElem;
    private String desc;
    private SortingAlgorithm alg;
    private SortingOrder order;

    public SortingTask() {
        super(-1,"");
    }

    public SortingTask(int id, String desc, SortingAlgorithm alg, SortingOrder order, int nrElem) {
        super(id,desc);
        this.id = id;
        this.desc = desc;
        this.alg = alg;
        this.order = order;
        this.nrElem=nrElem;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public SortingAlgorithm getAlg() {
        return alg;
    }

    public void setAlg(SortingAlgorithm alg) {
        this.alg = alg;
    }

    public SortingOrder getOrder() {
        return order;
    }

    public void setOrder(SortingOrder order) {
        this.order = order;
    }

    public int getNrElem() {
        return nrElem;
    }

    public void setNrElem(int nrElem) {
        this.nrElem = nrElem;
    }

    @Override
    public String toString() {
        return " Task: " + id +" "+ desc + " "+ order+' '+alg+' '+nrElem;
    }

    @Override
    public void execute() {
        for(int i=0;i<nrElem;i++){
            try{
                Thread.sleep(10);
            }catch(InterruptedException ex){
                System.out.println("Sorting task "+id+" : execute interrupted...");
               throw new TaskExecutionException(ex);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SortingTask){
            SortingTask st=(SortingTask)obj;
            return id==st.getId();
        }
        return false;
    }
}
