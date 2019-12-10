public class Consumator extends Thread {
    private BandaTransportoare banda;

    public Consumator(BandaTransportoare banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        super.run();

        try {
            int val;
            while (true) {

                synchronized (banda) {
                    while (banda.list.size() == 0) {
                        System.out.println("WAIT 1");
                        banda.wait();
                        if (banda.finish.get()) {
                            consume();
                            return;
                        }

                    }
                    val = banda.list.removeFirst();
                    System.out.println("Consumer1 " + val);
                    banda.notify();


                    while (banda.list.size() == 0) {
                        System.out.println("WAIT 2");

                        banda.wait();
                        if (banda.finish.get()) {
                            consume();
                            return;
                        }

                    }
                    val = banda.list.removeFirst();
                    System.out.println("Consumer2 " + val);
                    banda.notify();


                    while (banda.list.size() == 0) {
                        System.out.println("WAIT 3");

                        banda.wait();
                        if (banda.finish.get()) {
                            consume();
                            return;
                        }
                    }

                    val = banda.list.removeFirst();
                    System.out.println("Consumer3 " + val);
                    banda.notify();

                    Thread.sleep(8);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void consume() throws InterruptedException {
        int val;
        synchronized (banda) {
            while(banda.list.size()>0) {

                val = banda.list.removeFirst();
                System.out.println("Consumer1 " + val);


                if (banda.list.size() > 0) {
                    val = banda.list.removeFirst();
                    System.out.println("Consumer2 " + val);
                }


                if (banda.list.size() > 0) {
                    val = banda.list.removeFirst();
                    System.out.println("Consumer3 " + val);
                }

                Thread.sleep(8);
            }
        }
    }
}
