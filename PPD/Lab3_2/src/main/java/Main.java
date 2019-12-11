import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int noPolynomials = 10;
        int noThreads = 2;
        int minCoef = -100;
        int maxCoef = 100;
        int maxExp = 100;
        long start, finish;
        float time;
        // Generare polinoame

//        Utils.generatePolynomials(noPolynomials, minCoef, maxCoef, maxExp);

        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();

        ReaderThread readerThread = new ReaderThread(concurrentLinkedQueue, noPolynomials);
        readerThread.start();

        WriterThread[] writerThreads = new WriterThread[noThreads - 1];

        for (int i = 0; i < writerThreads.length; i++) {
            writerThreads[i] = new WriterThread(readerThread, doublyLinkedList, concurrentLinkedQueue);
        }

        start = System.nanoTime();

        for (int i = 0; i < writerThreads.length; i++) {
            writerThreads[i].start();
        }

        readerThread.join();

        for (int i = 0; i < writerThreads.length; i++) {
            writerThreads[i].join();
        }
        finish = System.nanoTime();

        doublyLinkedList.writeInFile("result1.txt");

        time = (finish - start) / (float) 1_000_000;
        System.out.println("Time: " + time + " ms");


        DoublyLinkedList linkedList1 = new DoublyLinkedList();
        ListThread[] threads1 = new ListThread[noThreads];
        long start1, finish1;
        float time1;

        for (int i = 0; i < noThreads; i++) {
            threads1[i] = new ListThread(noPolynomials, linkedList1, true);
        }

        start1 = System.nanoTime();

        for (int i = 0; i < noThreads; i++) {
            threads1[i].start();
        }

        for (int i = 0; i < noThreads; i++) {
            threads1[i].join();
        }

        finish1 = System.nanoTime();

        linkedList1.writeInFile("result.txt");

        time1 = (finish1 - start1) / (float) 1_000_000;
        System.out.println("Time: " + time1 + " ms list delete immediately");

        boolean isSame = Utils.isSameContentInFile("result.txt", "result1.txt");
        System.out.println(isSame);

    }
}
