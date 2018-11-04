- Abstract Factory pattern is similar to Factory pattern and it’s factory of factories. 
If you are familiar with factory design pattern in java, you will notice that we have 
a single Factory class that returns the different sub-classes based on the input provided 
and factory class uses if-else or switch statement to achieve this.

- In Abstract Factory pattern, we get rid of if-else block and have a factory class for 
each sub-class and then an Abstract Factory class that will return the sub-class based on 
the input factory class. 

- Abstract Factory Design Pattern Benefits:
     * provides approach to code for interface rather than implementation.
     * is “factory of factories” and can be easily extended to accommodate 
       more products.
     * is robust and avoid conditional logic of Factory pattern.