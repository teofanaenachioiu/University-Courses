package sem1_2.decorator;

import sem1_2.model.Container;
import sem1_2.model.Task;
import sem1_2.factory.TaskContainerFactory;
import sem1_2.factory.Strategy;

public class StrategyTaskRunner implements TaskRunner {

    private Container container;

    public StrategyTaskRunner(Strategy strategy)
    {
        container=new TaskContainerFactory().createContainer(strategy);
    }
    @Override
    public void executeOneTask() {
        Task task = container.remove();
        task.execute();
    }

    @Override
    public void executeAll() {
        while(!container.isEmpty())
            executeOneTask();
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
