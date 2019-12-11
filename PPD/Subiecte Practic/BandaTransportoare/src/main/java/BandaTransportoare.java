import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BandaTransportoare {
    public LinkedList<Integer> list;
    public int capacity;
    public int iteratii;
    public AtomicInteger value;
    public AtomicBoolean finish;

    public BandaTransportoare(int capacity) {
        this.capacity = capacity;
        this.list = new LinkedList<>();
        this.iteratii = 101;
        this.value = new AtomicInteger(1);
        this.finish = new AtomicBoolean(false);
    }
}
