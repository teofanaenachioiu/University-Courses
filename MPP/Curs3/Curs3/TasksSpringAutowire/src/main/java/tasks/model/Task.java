package tasks.model;

/**
 * Created by grigo on 9/27/16.
 */
public class Task {
    private int taskID;
    private String descriere;
    public Task(int id, String desc) {
        taskID=id;
        descriere=desc;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void execute(){
        System.out.println("Se executa ... "+taskID);
    }
    @Override
    public String toString(){
        return ""+taskID+' '+descriere;
    }
}
