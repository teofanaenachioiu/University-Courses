/*
Command Pattern Interface

We can use interface or abstract class to create our base Command, it’s a design
decision and depends on your requirement.

We are going with interface because we don’t have any default implementations.
 */

public interface Command {

    void execute();
}
