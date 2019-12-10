import java.util.concurrent.atomic.AtomicBoolean;

public class IterateThread extends Thread {
    private DictionarN1 dictionar;
    private AtomicBoolean potAfisa;

    public IterateThread(AtomicBoolean potAfisa, DictionarN1 dictionar) {
        this.dictionar = dictionar;
        this.potAfisa = potAfisa;
    }

    @Override
    public void run() {
        super.run();

        while (this.potAfisa.get()) {
            try {
                synchronized (dictionar) {
                    dictionar.wait();
                    //System.out.println("Am primit notificarea de trezire.");
                }

                //System.out.println("Vreau sa iterez dictionarul");
                if(!potAfisa.get()) break;
                synchronized (dictionar) {
                    dictionar.iterate();
                }

                synchronized (dictionar) {
                    dictionar.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Vreau sa iterez restul dictionarului ");
        dictionar.iterate();

        FileLogger.write("From iterate thread ");
    }
}
