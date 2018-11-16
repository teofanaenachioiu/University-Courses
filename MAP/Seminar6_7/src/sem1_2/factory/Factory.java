package sem1_2.factory;

import sem1_2.model.Container;

public interface Factory {
    Container createContainer(Strategy startegy);
}

