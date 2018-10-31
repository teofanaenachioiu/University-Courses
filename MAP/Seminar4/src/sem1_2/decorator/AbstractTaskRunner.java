package sem1_2.decorator;

import sem1_2.model.Task;

public abstract class AbstractTaskRunner implements TaskRunner {
    private TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner t){
        taskRunner=t;
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
        //taskRunner.executeAll();
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
