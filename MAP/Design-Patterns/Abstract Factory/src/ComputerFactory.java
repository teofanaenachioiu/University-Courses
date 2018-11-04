/*
Now we will create a consumer class that will provide the entry point
for the client classes to create sub-classes.

Notice that its a simple class and getComputer method is accepting
ComputerAbstractFactory argument and returning Computer object.
 */

public class ComputerFactory {

    public static Computer getComputer(ComputerAbstractFactory factory){
        return factory.createComputer();
    }
}

