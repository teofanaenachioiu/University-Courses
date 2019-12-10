import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddUpdateThread extends Thread {
    private DictionarN1 dictionar;
    private List<Message> messageList;
    private int N;
    private static Counter counter = new Counter();
    private static AtomicBoolean haveToSleep = new AtomicBoolean(false);
    private AtomicBoolean potAfisa;

    public AddUpdateThread(AtomicBoolean potAfisa , DictionarN1 dictionar, List<Message> listaMessage, int N) {
        this.potAfisa = potAfisa;
        this.dictionar = dictionar;
        this.messageList = listaMessage;
        this.N = N;
    }


    @Override
    public void run() {
        super.run();

        while (potAfisa.get()) {
            int index = counter.get();

            if (index < messageList.size()) {
                if (index % N == 0 || haveToSleep.get()) {
                    haveToSleep.set(true);
                    synchronized (dictionar) {
                        dictionar.notify();
                    }

                    synchronized (dictionar) {
                        try {
                            dictionar.wait();
                            //System.out.println("Treadul de adaugat s-a trezit");
                            haveToSleep.set(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //System.out.println("Index: "+index);
                Message message = messageList.get(index);
                dictionar.pushValue(message.getNume(), message.getNota());
            } else {
                potAfisa.set(false);

            }
        }
        synchronized (dictionar) {
            dictionar.notifyAll();
        }
        FileLogger.write("From insert/update thread ");
    }
}
