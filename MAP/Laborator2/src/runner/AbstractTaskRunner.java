package runner;

import model.Task;

public abstract class AbstractTaskRunner implements TaskRunner {
    protected TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner runner) {
        this.taskRunner = runner;
    }
    @Override
    public abstract void executeOneTask();

    @Override
    public abstract void executeAll();

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
