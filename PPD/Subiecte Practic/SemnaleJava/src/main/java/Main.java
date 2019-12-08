import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int nr_threaduri_eliminare_si_bruiaje = 2;
        int nr_threaduri_clasificare = 10;
        int nr_intervale = 50;
        int nr_semnale = 100000;


        Utils.generareSemnale(nr_semnale);

        Utils.generareBruiaje(nr_threaduri_eliminare_si_bruiaje);

        DoublyLinkedList listaSemnale = new DoublyLinkedList();

        Utils.readSemnaleFromFile(listaSemnale);

        List<Integer> bruiaje = Utils.readBruiajFromFile();

        EliminareThread[] threads = new EliminareThread[nr_threaduri_eliminare_si_bruiaje];

        for (int i = 0; i < nr_threaduri_eliminare_si_bruiaje; i++) {
            threads[i] = new EliminareThread(bruiaje.get(i), listaSemnale);
        }
        for (int i = 0; i < nr_threaduri_eliminare_si_bruiaje; i++) {
            threads[i].start();
        }
        for (int i = 0; i < nr_threaduri_eliminare_si_bruiaje; i++) {
            threads[i].join();
        }

        Utils.generareIntervale(nr_intervale);
        List<Interval> intervale = Utils.readIntervaleFromFile(nr_intervale);

        ClasificareThread[] threadsClasif = new ClasificareThread[nr_threaduri_clasificare];
        for (int i = 0; i < nr_threaduri_clasificare; i++) {
            threadsClasif[i] = new ClasificareThread(intervale, listaSemnale);
        }
        for (int i = 0; i < nr_threaduri_clasificare; i++) {
            threadsClasif[i].start();
        }
        for (int i = 0; i < nr_threaduri_clasificare; i++) {
            threadsClasif[i].join();
        }

        //listaSemnale.display();

    }
}
