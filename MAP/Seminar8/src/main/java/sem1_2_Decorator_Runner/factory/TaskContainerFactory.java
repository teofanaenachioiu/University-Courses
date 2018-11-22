package sem1_2_Decorator_Runner.factory;

import sem1_2_Decorator_Runner.model.Container;
import sem1_2_Decorator_Runner.model.StackContainer;

public class TaskContainerFactory implements Factory {
    @Override
    public Container createContainer(Strategy startegy) {
        if (startegy==Strategy.FIFO)
            return null;//Pe vitor vom returna new queueContainer
        else
            return new StackContainer();
    }
}
