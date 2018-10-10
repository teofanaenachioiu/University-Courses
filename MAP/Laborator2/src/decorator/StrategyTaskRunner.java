package decorator;

import factory.TaskContainerFactory;
import model.Container;
import model.Strategy;
import model.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        this.container = new TaskContainerFactory().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        Task t=this.container.remove();
        t.execute();
    }

    @Override
    public void executeAll() {
        while(!this.container.isEmpty()){
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        this.container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !this.container.isEmpty();
    }
}
