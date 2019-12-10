import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateThread extends Thread {
    private AtomicBoolean isProcessing;
    private AtomicInteger errorsNumber;
    private List<Muchie> muchii;
    private Graf graf;
    private static Counter counter = new Counter();
    private int id;

    public UpdateThread(AtomicBoolean isProcessing, AtomicInteger errorsNumber, Graf graf, List<Muchie> muchii, int id) {
        this.isProcessing = isProcessing;
        this.graf = graf;
        this.muchii = muchii;
        this.id = id;
        this.errorsNumber = errorsNumber;
    }

    @Override
    public void run() {
        super.run();

        while(isProcessing.get()){
            int index = counter.get();
            System.out.println("Index: " + index);
            if(index<muchii.size()) {
                Muchie muchie = muchii.get(index);

                if (graf.verificaMuchie(muchie.getNod_1(), muchie.getNod_2())) {
                    graf.insereazaMuchie(muchie.getNod_1(), muchie.getNod_2(), muchie.getValue());
                } else {
                    synchronized (muchii) {
                        this.errorsNumber.incrementAndGet();
                        System.out.println("Am gasit o eroare si notific threadul de erori");
                        muchii.notify();
                        try {
                            muchii.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                isProcessing.set(false);
            }
        }
        System.out.println("Am terminat de procesat " + id);
    }
}
