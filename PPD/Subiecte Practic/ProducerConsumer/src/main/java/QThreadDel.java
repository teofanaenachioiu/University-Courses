import java.util.concurrent.atomic.AtomicInteger;

public class QThreadDel extends Thread {
    private MyQueue<Integer> queue;
    private int id;

    public QThreadDel(MyQueue<Integer> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    @Override
    public void run() {
        super.run();

        while(true) {
            try {
                int val = queue.consume();
                System.out.println("consume " + id + ": " + val);
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
