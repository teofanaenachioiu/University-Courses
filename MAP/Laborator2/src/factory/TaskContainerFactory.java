package factory;

import model.*;

import java.util.ArrayList;
//ex 7
public final class TaskContainerFactory implements Factory {

    private static final TaskContainerFactory INSTANCE = new TaskContainerFactory();

    private TaskContainerFactory() {}

    public static TaskContainerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        if(strategy==Strategy.LIFO) return new StackContainer();
        else return new QueueContainer();
    }
}

