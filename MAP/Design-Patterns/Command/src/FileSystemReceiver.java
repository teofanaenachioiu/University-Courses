/*
Command Pattern Receiver Classes

FileSystemReceiver interface defines the contract for the implementation classes.
 */

public interface FileSystemReceiver {

    void openFile();
    void writeFile();
    void closeFile();
}
