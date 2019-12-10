import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ErrorThread extends Thread {
    private AtomicBoolean isProcessing;
    private AtomicInteger errorsNumber;
    private List<Muchie> muchii;

    public ErrorThread(AtomicBoolean isProcessing, AtomicInteger errorsNumber, List<Muchie> muchii) {
        this.isProcessing = isProcessing;
        this.muchii = muchii;
        this.errorsNumber = errorsNumber;
    }

    @Override
    public void run() {
        super.run();
        while(isProcessing.get()){
            synchronized (muchii){
                try {
                    muchii.wait();
                    System.out.println("!!! Am dat de o eroare !!!");
                    muchii.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Error thread is out");
    }
}
