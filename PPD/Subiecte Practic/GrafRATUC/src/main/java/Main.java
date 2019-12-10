import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<Muchie> muchiiUpdate = Utils.readFromFile("graph_problema1");

        Graf graf = new Graf();

        AtomicBoolean isProcessing = new AtomicBoolean(true);
        AtomicInteger errorNumbers = new AtomicInteger(0);

        UpdateThread updateThread1 = new UpdateThread(isProcessing, errorNumbers, graf, muchiiUpdate, 1);
        UpdateThread updateThread2 = new UpdateThread(isProcessing, errorNumbers, graf, muchiiUpdate, 2);
        ErrorThread errorThread = new ErrorThread(isProcessing,errorNumbers, muchiiUpdate);

        updateThread1.start();
        updateThread2.start();
        errorThread.start();

        updateThread1.join();
        updateThread2.join();
        errorThread.join();
    }
}
