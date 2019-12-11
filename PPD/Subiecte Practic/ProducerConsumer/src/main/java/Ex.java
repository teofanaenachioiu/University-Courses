import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class ProducerBuf extends Thread {
    private Buffer buffer;
    private String name;
    private static AtomicInteger val = new AtomicInteger(1);
    private static AtomicInteger index = new AtomicInteger(0);
    private static int iteratii = 20;

    ProducerBuf(Buffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        int it = index.getAndIncrement();
        while (it < iteratii) {
            System.out.println("ITERATIA " + it + " "+ name);
            try {
                buffer.produce(val.getAndIncrement(), name);
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            it = index.getAndIncrement();
        }
        System.out.println("Am terminat de produs " + name);

        buffer.setFinish(new AtomicBoolean(true));
    }
}

class Buffer {
    private Vector<Integer> buffer;
    private int cap;
    private AtomicBoolean finish = new AtomicBoolean(false);


    Buffer(int cap) {
        buffer = new Vector<Integer>();
        this.cap = cap;
    }

    public synchronized AtomicBoolean getFinish() {
        return finish;
    }

    public synchronized void setFinish(AtomicBoolean finish) {
        this.finish = finish;
        notifyAll();
    }


    public int size() {
        return buffer.size();
    }

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private void add(int x) {
        buffer.add(x);
    }

    private int remove() {
        if (buffer.isEmpty())
            return -1;
        int x = buffer.firstElement();
        buffer.remove(buffer.firstElement());
        return x;
    }

    public synchronized void produce(int x, String name) throws InterruptedException {
        while (buffer.size() == this.cap ) {
            if(getFinish().get()) return;
            System.out.println("Bufferul e plin. Astept..."  + name);
            wait();
        }
        buffer.add(x);
        System.out.println("Am adaugat " + x + " " + name);
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.size() == 0) {
            System.out.println("Bufferul e gol. Astept...");
            wait();
        }
        int x = this.remove();
        System.out.println("Am sters " + x);
        notifyAll();
    }

}

class ConsumerBuf extends Thread {
    private Buffer buffer;
    private String name;

    ConsumerBuf(Buffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.consume();
                Thread.sleep(120);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(buffer.getFinish().get()) break;
        }
        System.out.println("Consumatorul "+name + " a terminat");
    }
}

public class Ex {
    public static void main(String args[]) throws InterruptedException {
        Buffer b = new Buffer(5);
        ProducerBuf p1 = new ProducerBuf(b, "P1");
        ProducerBuf p2 = new ProducerBuf(b, "P2");
        ProducerBuf p3 = new ProducerBuf(b, "P3");
        ConsumerBuf c1 = new ConsumerBuf(b, "C1");
        ConsumerBuf c2 = new ConsumerBuf(b, "C2");
        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        p1.join();
        p2.join();
        p3.join();
        c1.join();
        c2.join();

        System.out.println(b.size());
    }
}