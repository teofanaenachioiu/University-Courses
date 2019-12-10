import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;

        List<Message> listaInsertUpdate = new ArrayList<>();
        List<String> listaDelete = new ArrayList<>();

        DictionarN1 dictionar = new DictionarN1();

        Utils.readFromFile(listaInsertUpdate, listaDelete);

        AtomicBoolean potAfisa = new AtomicBoolean(true);

        AddUpdateThread addUpdateThread1 = new AddUpdateThread(potAfisa,dictionar, listaInsertUpdate, N);
        AddUpdateThread addUpdateThread2 = new AddUpdateThread(potAfisa,dictionar, listaInsertUpdate, N);
        IterateThread iterateThread = new IterateThread(potAfisa,dictionar);
        DeleteThread deleteThread = new DeleteThread(dictionar, listaDelete);
        addUpdateThread1.start();
        addUpdateThread2.start();
        iterateThread.start();
        deleteThread.start();

        addUpdateThread1.join();
        addUpdateThread2.join();
        deleteThread.join();
        iterateThread.join();

    }
}
