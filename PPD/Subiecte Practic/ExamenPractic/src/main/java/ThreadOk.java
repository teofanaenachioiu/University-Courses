import java.util.concurrent.atomic.AtomicInteger;

public class ThreadOk extends Thread {
    private AtomicInteger okeis;
    private int n;
    private ContBancar contBancar;

    public ThreadOk(ContBancar contBancar, AtomicInteger okeis, int n) {
        this.okeis = okeis;
        this.n = n;
        this.contBancar = contBancar;
    }


    @Override
    public void run() {
        super.run();

        long t = System.currentTimeMillis();
        long end = t + 2000;
        while (System.currentTimeMillis() < end) {
            synchronized (contBancar) {
                try {

                    contBancar.wait();
                    if (okeis.get() < n - 1) {
                        okeis.getAndIncrement();
                    } else {
                        okeis.set(0);
                        contBancar.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
