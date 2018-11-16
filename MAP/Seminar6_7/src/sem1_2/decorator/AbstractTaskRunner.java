package sem1_2.decorator;

import sem1_2.model.Task;

//abstract decorator
public class AbstractTaskRunner implements TaskRunner{
    private TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
    }

    @Override
    public void executeAll() {
        while(hasTask())
            executeOneTask();


    }

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);

    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
