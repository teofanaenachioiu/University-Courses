public class Producator extends Thread {
    private BandaTransportoare banda;
    private static Counter counter = new Counter();

    public Producator(BandaTransportoare banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        super.run();
        int val;
        while (true) {
            int it = counter.get();

            if (it >= banda.iteratii)
                break;

            synchronized (banda) {
                try {
                    while (banda.list.size() == banda.capacity) {
                        banda.wait();
                    }

                    val = banda.value.getAndIncrement();
                    banda.list.add(val);
                    System.out.println("Producer " + val);
                    banda.notify();


                    while (banda.list.size() == banda.capacity) {
                        banda.wait();
                    }

                    val = banda.value.getAndIncrement();
                    banda.list.add(val);
                    System.out.println("Producer " + val);
                    banda.notify();


                    while (banda.list.size() == banda.capacity) {
                        banda.wait();
                    }

                    val = banda.value.getAndIncrement();
                    banda.list.add(val);
                    System.out.println("Producer " + val);
                    banda.notify();


                    while (banda.list.size() == banda.capacity) {
                        banda.wait();
                    }

                    val = banda.value.getAndIncrement();
                    banda.list.add(val);
                    System.out.println("Producer " + val);
                    banda.notify();

                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("AM AJUNS LA NUMARUL MAXIM DE ITERATII " + banda.iteratii);

        synchronized (banda) {
            banda.finish.set(true);
            banda.notifyAll();
        }
    }
}
