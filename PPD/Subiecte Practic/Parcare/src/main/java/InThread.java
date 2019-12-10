import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InThread extends Thread {
    private MyLinkedList linkedList;
    private AtomicBoolean isProcessing;
    private static AtomicInteger count = new AtomicInteger(0);
    private int iteratii = 200;

    public InThread(AtomicBoolean isProcessing, MyLinkedList linkedList) {
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
                    System.out.println("Locuri ocupate inainte de verif: "+ linkedList.getNumarLocuriOcupate());
                    while (linkedList.getNumarLocuriOcupate() == linkedList.getNumarLocuri()) {
                        System.out.println("Astept sa se elibereze locuri");
                        linkedList.wait();
                    }

                    linkedList.insert(TransactionType.INTRARE);
                    System.out.println("INTRARE");
                    System.out.println("Locuri ocupate in : "+ linkedList.getNumarLocuriOcupate());
                    linkedList.notify();
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("End add");
        this.isProcessing.set(false);
    }
}
