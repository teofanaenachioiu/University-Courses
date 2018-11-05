- Command design pattern is used to implement loose coupling in a request-response model.

- In command pattern, the request is send to the invoker and invoker pass it to the encapsulated 
command object.

- Command object passes the request to the appropriate method of Receiver to perform the specific 
action.

- Command Pattern Important Points:
     * Command is the core of command design pattern that defines the contract for implementation.
     * Receiver implementation is separate from command implementation.
     * Command implementation classes chose the method to invoke on receiver object, for every 
       method in receiver there will be a command implementation. It works as a bridge between 
       receiver and action methods.
     * Invoker class just forward the request from client to the command object.
     * Client is responsible to instantiate appropriate command and receiver implementation and 
       then associate them together.
     * Client is also responsible for instantiating invoker object and associating command object 
       with it and execute the action method.
     * Command design pattern is easily extendible, we can add new action methods in receivers and 
       create new Command implementations without changing the client code.
     * The drawback with Command design pattern is that the code gets huge and confusing with high 
       number of action methods and because of so many associations.