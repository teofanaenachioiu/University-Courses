package tasks.utils;


import tasks.model.Task;

/**
 * Created by grigo on 11/9/16.
 */
public class TaskEvent implements Event {
    private TaskEventType type;
    private Task task;
    public TaskEvent(TaskEventType type, Task task) {
        this.task=task;
        this.type=type;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskEventType getType() {
        return type;
    }

    public void setType(TaskEventType type) {
        this.type = type;
    }
}
