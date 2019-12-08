// Java program to illustrate
// ThreadPool

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task extends Thread {
    private int nivel;
    private int id;
    private Numar[] numere;
    private int p;
    private int k;
    private Task[] threads;

    public Task(int id, Numar[] numere, int p, int k, Task[] threads) {
        this.id = id;
        this.numere = numere;
        this.p = p;
        this.k = k;
        this.threads = threads;
        this.nivel = 0;
    }

    public synchronized int getNivel() {
        return this.nivel;
    }

    public synchronized void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public void run() {
        int pow = 2;
        for (int i = 0; i < k; i++) {
            // nivelul la care suntem
            if (id % pow == 0) {
                synchronized (numere[id]) {
                    while (this.getNivel() != threads[id + pow / 2].getNivel()) {
                        try {
                            numere[id].wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //adunare numere
                    numere[id].aduna(numere[id + pow / 2]);

                    setNivel(getNivel() + 1);
                    if (id - pow >= 0) {
                        synchronized (numere[id - pow]) {
                            numere[id - pow].notify();
                        }
                    }
                }
            }
            pow *= 2;
        }
    }
}

public class Exemple {
    static final int P = 3;
    static final int N = 10;

    public static void main(String[] args) {

        Numar[] numere = Utils.generateNumber(N);

        int sumaCorecta = Utils.addSeq(numere, N);
        System.out.println(sumaCorecta );

        int k = (int) (Math.log(P) / Math.log(2));
        Task[] runnables = new Task[N];

        for (int i = 0; i < N; i++) {
            runnables[i] = new Task(i, numere, P, k, runnables);
        }

        ExecutorService pool = Executors.newFixedThreadPool(P);

        for (int i = 0; i < N; i++) {
            pool.execute(runnables[i]);
        }
        pool.shutdown();
        System.out.println(numere[0].getNumber());
    }
}
