import java.io.IOException;
import java.util.List;

public class ClasificareThread extends Thread {
    private List<Interval> intervale;
    private DoublyLinkedList listaSemnale;
    private static Counter counter = new Counter();

    public ClasificareThread(List<Interval> intervale, DoublyLinkedList listaSemnale) {
        this.intervale = intervale;
        this.listaSemnale = listaSemnale;
    }

    @Override
    public void run() {
        super.run();
        int index = counter.value();
        while (index < intervale.size()) {
            counter.increment();
            Interval interval = intervale.get(index);

            try {
                listaSemnale.writeInFile("frecventa_"+index+".txt", interval);
            } catch (IOException e) {
                e.printStackTrace();
            }
            index = counter.value();
        }
    }
}
