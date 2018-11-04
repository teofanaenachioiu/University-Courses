/*
First of all we need to create a Abstract Factory interface or abstract class.

Notice that createComputer() method is returning an instance of super class Computer.
Now our factory classes will implement this interface and return their respective sub-class.
 */

public interface ComputerAbstractFactory {
    public Computer createComputer();

}
