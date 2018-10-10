package factory;

import model.Container;
import model.StackContainer;
import model.Strategy;

public class TaskContainerFactory implements Factory{

    @Override
    public Container createContainer(Strategy strategy) {
        if(strategy==Strategy.LIFO) return  new StackContainer();
        //else return new QueueContainer();
        return null;
    }
}
