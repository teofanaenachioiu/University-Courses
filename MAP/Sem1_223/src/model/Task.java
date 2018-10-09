package model;

public abstract class Task {
    private String taskId;
    private String descriere;

    public Task(String taskId, String descriere) {
        this.taskId = taskId;
        this.descriere = descriere;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return
                "id=" + taskId + '|' +
                "descriere=" + descriere;
    }

    public abstract void execute();
}
