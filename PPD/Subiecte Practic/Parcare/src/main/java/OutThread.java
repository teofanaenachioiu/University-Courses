import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class OutThread extends Thread {
    private MyLinkedList linkedList;
    private AtomicBoolean isProcessing;
    private static AtomicInteger count = new AtomicInteger(0);
    private int iteratii = 150;

    public OutThread(AtomicBoolean isProcessing, MyLinkedList linkedList) {
        this.linkedList = linkedList;
        this.isProcessing = isProcessing;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            int index = count.getAndIncrement();
            if (index >= iteratii) break;
            try {
                synchronized (this.linkedList) {
                    while (linkedList.getNumarLocuriOcupate() == 0) {
                        System.out.println("Astept sa se ocupe locuri");
                        linkedList.wait();
                    }

                    linkedList.insert(TransactionType.IESIRE);
                    System.out.println("IESIRE");
                    System.out.println("Locuri ocupate out : "+ linkedList.getNumarLocuriOcupate());
                    linkedList.notify();
                }

                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("End delete");

    }
}
