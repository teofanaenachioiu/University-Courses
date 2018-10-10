package decorator;

import model.Task;

public abstract class AbstractTaskRunner implements TaskRunner {
    private TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner t) {
        this.taskRunner = t;
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
    }

    @Override
    public void executeAll() {
        while(hasTask()){
            executeOneTask();
        }
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
