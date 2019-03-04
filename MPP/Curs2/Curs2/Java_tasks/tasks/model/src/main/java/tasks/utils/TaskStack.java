package tasks.utils;

import tasks.model.Task;

/**
 * Created by grigo on 9/27/16.
 */
public class TaskStack implements Container {
    private TaskArray tasks;

    public TaskStack() {
        tasks=new TaskArray();
    }

    @Override
    public Task remove() {
        return tasks.delete(tasks.getSize()-1);
    }

    @Override
    public void add(Task task) {
        tasks.add(task);

    }

    @Override
    public int size() {
        return tasks.getSize();
    }

    @Override
    public boolean isEmpty() {
        return tasks.getSize()==0;
    }

    @Override
    public Iterable<Task> getAll() {
        return tasks.getAll();
    }
}
