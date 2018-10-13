package factory;
import model.Container;
import model.Strategy;

public interface Factory {
    Container createContainer(Strategy strategy);

}
