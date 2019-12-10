import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 0;
        int p = 100;
        MyLinkedList linkedList = new MyLinkedList(n, p);

        int th_in = 2;
        int th_out = 2;
        AtomicBoolean isProcessing = new AtomicBoolean(true);

        InThread[] inThreads = new InThread[th_in];
        OutThread[] outThreads = new OutThread[th_out];
        PrintThread printThread = new PrintThread(isProcessing, linkedList);

        // Create
        for (int i = 0; i < th_in; i++) {
            inThreads[i] = new InThread(isProcessing,linkedList);
        }

        for (int i = 0; i < th_out; i++) {
            outThreads[i] = new OutThread(isProcessing,linkedList);
        }

        // Start
        for (int i = 0; i < th_in; i++) {
            inThreads[i].start();
        }

        for (int i = 0; i < th_out; i++) {
            outThreads[i].start();
        }

        printThread.start();

        // Join
        for (int i = 0; i < th_in; i++) {
            inThreads[i].join();
        }
        for (int i = 0; i < th_out; i++) {
            outThreads[i].join();
        }

        printThread.join();

        System.out.println(linkedList.getNumarLocuriOcupate());

    }
}
