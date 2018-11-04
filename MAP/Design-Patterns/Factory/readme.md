- Factory design pattern is used when we have a super class with multiple 
sub-classes and based on input, we need to return one of the sub-class. 
This pattern take out the responsibility of instantiation of a class from 
client program to the factory class.

- Some important points about Factory Design Pattern method are:
     * We can keep Factory class Singleton or we can keep the method that 
       returns the subclass as static.
     * Notice that based on the input parameter, different subclass is 
       created and returned. getComputer is the factory method.