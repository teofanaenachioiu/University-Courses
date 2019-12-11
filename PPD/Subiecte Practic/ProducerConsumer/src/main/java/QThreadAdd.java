import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QThreadAdd extends Thread {
    private MyQueue<Integer> queue;
    private static AtomicInteger val = new AtomicInteger(1);
    private int id;

    public QThreadAdd(MyQueue<Integer> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                queue.produce(val.getAndIncrement());
                System.out.println("produce " + id + ": " + val);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
