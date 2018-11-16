package sem1_2.decorator;

import sem1_2.factory.TaskContainerFactory;
import sem1_2.model.Container;
import sem1_2.model.Strategy;
import sem1_2.model.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        this.container = new TaskContainerFactory().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        Task currentTask = container.remove();
        currentTask.execute();
    }

    @Override
    public void executeAll() {
        while(container.isEmpty() == false) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        this.container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
