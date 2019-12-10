import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrintThread extends Thread {
    private MyLinkedList linkedList;
    private AtomicBoolean isProcessing;

    public PrintThread(AtomicBoolean isProcessing, MyLinkedList linkedList) {
        this.linkedList = linkedList;
        this.isProcessing = isProcessing;
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (linkedList) {
                try {
                    if (linkedList.isNewData()) {
                        Iterator iterator = linkedList.getIterator();
                        while (iterator.hasNext()) {
                            Node node = (Node) iterator.next();
                            System.out.println(node);
                        }
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isProcessing.get()) {
                    break;
                }
            }
        }
    }
}