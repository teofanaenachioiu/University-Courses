- Decorator design pattern is used to modify the functionality of an object at runtime. 
At the same time other instances of the same class will not be affected by this, so individual 
object gets the modified behavior. 

- We use inheritance or composition to extend the behavior of an object but this is done at 
compile time and its applicable to all the instances of the class. We can’t add any new 
functionality of remove any existing behavior at runtime – this is when Decorator pattern 
comes into picture.

- Important Points:
     * Decorator design pattern is helpful in providing runtime modification abilities and 
       hence more flexible. Its easy to maintain and extend when the number of choices are 
       more.
     * The disadvantage of decorator design pattern is that it uses a lot of similar kind 
       of objects (decorators).
     * Decorator pattern is used a lot in Java IO classes, such as FileReader, BufferedReader 
       etc. 