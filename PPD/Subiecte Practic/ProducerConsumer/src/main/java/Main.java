public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyQueue<Integer> queue = new MyQueue<Integer>();
        int nrAdd = 3;
        int nrDel = 4;

        QThreadAdd[] threadAdds = new QThreadAdd[nrAdd];
        QThreadDel[] threadDels = new QThreadDel[nrDel];

        for (int i = 0; i < nrAdd; i++) {
            threadAdds[i] = new QThreadAdd(queue,i+1);
        }
        for (int i = 0; i < nrDel; i++) {
            threadDels[i] = new QThreadDel(queue,i+1);
        }

        for (int i = 0; i < nrAdd; i++) {
            threadAdds[i].start();
        }

        for (int i = 0; i < nrDel; i++) {
            threadDels[i].start();
        }

        for (int i = 0; i < nrAdd; i++) {
            threadAdds[i].join();
        }

        for (int i = 0; i < nrDel; i++) {
            threadDels[i].join();
        }

        System.out.println("GATA");
    }
}
