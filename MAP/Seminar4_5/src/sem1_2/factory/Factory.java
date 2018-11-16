package sem1_2.factory;
import sem1_2.model.Container;
import sem1_2.model.Strategy;

public interface Factory {
    Container createContainer(Strategy strategy);
}

