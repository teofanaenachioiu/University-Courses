package sem1_2_Decorator_Runner.factory;

import sem1_2_Decorator_Runner.model.Container;

public interface Factory {
    Container createContainer(Strategy startegy);
}

