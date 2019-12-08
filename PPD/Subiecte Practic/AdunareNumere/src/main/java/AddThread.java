public class AddThread extends Thread {
    private int nivel;
    private int id;
    private Numar[] numere;
    private int p;
    private int k;
    private AddThread[] threads;

    public AddThread(int id, Numar[] numere, int p, int k, AddThread[] threads) {
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
        super.run();

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
