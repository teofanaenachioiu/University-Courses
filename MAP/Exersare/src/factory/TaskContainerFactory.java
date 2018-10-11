package factory;

import model.*;

import java.util.ArrayList;

public class TaskContainerFactory implements Factory {
    @Override
    public Container createContainer(Strategy strategy) {
        if(strategy==Strategy.LIFO) return new StackContainer();
        else return new QueueContainer();
    }
}
