import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPersoana extends Thread {
    private ContBancar contBancar;
    private AtomicInteger okeis;
    private int id;
    private int n;

    public ThreadPersoana(ContBancar contBancar, AtomicInteger okeyuri, int id, int n) {
        this.contBancar = contBancar;
        this.id = id;
        this.okeis = okeyuri;
        this.n = n;
    }

    @Override
    public void run() {
        super.run();

        long t = System.currentTimeMillis();
        long end = t + 2000;
        while (System.currentTimeMillis() < end) {

            Random r = new Random();

            boolean depunere = r.nextBoolean();
            int suma = r.nextInt(100 + 1);
            TipValuta valuta = TipValuta.values()[new Random().nextInt(TipValuta.values().length)];

            try {
                if (depunere) {
                    if (valuta.equals(TipValuta.RON)) contBancar.depuneRon(suma);
                    if (valuta.equals(TipValuta.DOLAR)) contBancar.depuneDolar(suma);
                    if (valuta.equals(TipValuta.EURO)) contBancar.depuneEuro(suma);
                } else {

                    synchronized (contBancar) {
                        //setez ok = 0
                        contBancar.notifyAll();

                        while (okeis.get() != n - 1) {
                            System.out.println("Astept sa primesc n-1 okey-uri..." + okeis.get());
                            contBancar.wait();
                        }
                        //okeis.set(0);
                        //contBancar.notifyAll();
                    }

                    //Thread.sleep(50);

                    if (valuta.equals(TipValuta.RON)) contBancar.retrageRon(suma);
                    if (valuta.equals(TipValuta.DOLAR)) contBancar.retrageDolar(suma);
                    if (valuta.equals(TipValuta.EURO)) contBancar.retrageEuro(suma);
                }

                int timp = r.nextInt(20 + 1 - 10) + 10;
                Thread.sleep(timp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
