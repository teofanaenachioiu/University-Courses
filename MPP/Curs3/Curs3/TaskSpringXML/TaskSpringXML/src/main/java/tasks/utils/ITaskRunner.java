package tasks.utils;

import tasks.model.Task;

/**
 * Created by grigo on 10/11/16.
 */
public interface ITaskRunner {
    void executeOneTask();
    void executeAll();
    void addTask(Task t);
}
