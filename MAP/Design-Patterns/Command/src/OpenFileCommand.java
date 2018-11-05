/*
Now we need to create implementations for all the different types of action
performed by the receiver. Since we have three actions we will create three
Command implementations. Each Command implementation will forward the request
to the appropriate method of receiver.
 */

public class OpenFileCommand implements Command {

    private FileSystemReceiver fileSystem;

    public OpenFileCommand(FileSystemReceiver fs){
        this.fileSystem=fs;
    }
    @Override
    public void execute() {
        //open command is forwarding request to openFile method
        this.fileSystem.openFile();
    }

}
