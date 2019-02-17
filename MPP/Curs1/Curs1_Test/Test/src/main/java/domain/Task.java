package domain;

import domain.HasID;

public abstract class Task implements HasID<String> {
    private String taskId;
    private String desc;

    public Task(String taskId, String desc) {
        this.taskId = taskId;
        this.desc = desc;
    }

    public abstract void execute();

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return taskId+";"+desc;
    }

    @Override
    public String getId() {
        return taskId;
    }

    @Override
    public void setId(String s) {
        taskId=s;
    }
}