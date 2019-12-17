public class ThreadPrint extends Thread {
    private ContBancar contBancar;

    public ThreadPrint(ContBancar contBancar) {
        this.contBancar = contBancar;
    }


    @Override
    public void run() {
        super.run();

        long t= System.currentTimeMillis();
        long end = t+2000;

        while(System.currentTimeMillis() < end){
            contBancar.scrieInFisier();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
