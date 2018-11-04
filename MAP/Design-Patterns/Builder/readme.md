- This pattern was introduced to solve some of the problems with Factory and Abstract 
Factory design patterns when the Object contains a lot of attributes. 

- Builder pattern solves the issue with large number of optional parameters and inconsistent 
state by providing a way to build the object step-by-step and provide a method that will 
actually return the final Object. 

- Implementaton of Builder Design Pattern in Java:
     * First of all you need to create a static nested class and then copy all the arguments 
       from the outer class to the Builder class. We should follow the naming convention and 
       if the class name is Computer then builder class should be named as ComputerBuilder.
     * Java Builder class should have a public constructor with all the required attributes 
       as parameters.
     * Java Builder class should have methods to set the optional parameters and it should 
       return the same Builder object after setting the optional attribute.
     * The final step is to provide a build() method in the builder class that will return 
       the Object needed by client program. For this we need to have a private constructor 
       in the Class with Builder class as argument.