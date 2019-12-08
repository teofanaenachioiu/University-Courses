package collections;

import threads.ReaderThread;

public class Main {
    public static void main(String[] args) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        ReaderThread readerThread = new ReaderThread(concurrentLinkedQueue, 2);
    }
}
