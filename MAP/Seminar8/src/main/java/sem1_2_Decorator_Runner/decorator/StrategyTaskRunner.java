package sem1_2_Decorator_Runner.decorator;

import sem1_2_Decorator_Runner.model.Container;
import sem1_2_Decorator_Runner.model.Task;
import sem1_2_Decorator_Runner.factory.TaskContainerFactory;
import sem1_2_Decorator_Runner.factory.Strategy;

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
