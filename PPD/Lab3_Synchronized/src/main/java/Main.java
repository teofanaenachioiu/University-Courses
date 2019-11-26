import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int noPolynomials = 10;
        int noThreads = 4;
        int minCoef = -100;
        int maxCoef = 100;
        int maxExp = 10000;

        // Generare polinoame

        //Utils.generatePolynomials(noPolynomials, minCoef, maxCoef, maxExp);


        // Sincronizare la nivel de lista
        // Stergerea imediata a unui nod cu coeficientul 0

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
        System.out.println("Time: " + time1 + " ms list");


        // Sincronizare la nivel de nod
        // Stergerea imediata a unui nod cu coeficientul 0

        DoublyLinkedList linkedList = new DoublyLinkedList();
        NodeThread[] threads = new NodeThread[noThreads];
        long start, finish;
        float time;

        linkedList.prepare(maxExp);

        for (int i = 0; i < noThreads; i++) {
            threads[i] = new NodeThread(noPolynomials, linkedList, true);
        }

        start = System.nanoTime();

        for (int i = 0; i < noThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < noThreads; i++) {
            threads[i].join();
        }

        finish = System.nanoTime();

        linkedList.broke();
        linkedList.writeInFile("result1.txt");

        time = (finish - start) / (float) 1_000_000;
        System.out.println("Time: " + time + " ms node");


        // Verificare rezultate

        System.out.println("Result: " + Utils.isSameContentInFile("result.txt", "result1.txt"));



        // Sincronizare la nivel de lista
        // Stergerea la final a tuturor coeficientilor egali cu 0

        DoublyLinkedList linkedList2 = new DoublyLinkedList();
        ListThread[] threads2 = new ListThread[noThreads];
        long start2, finish2;
        float time2;

        for (int i = 0; i < noThreads; i++) {
            threads2[i] = new ListThread(noPolynomials, linkedList2, false);
        }

        start2 = System.nanoTime();

        for (int i = 0; i < noThreads; i++) {
            threads2[i].start();
        }

        for (int i = 0; i < noThreads; i++) {
            threads2[i].join();
        }

        linkedList2.deleteZeros();

        finish2 = System.nanoTime();

        linkedList2.writeInFile("result.txt");

        time2 = (finish2 - start2) / (float) 1_000_000;
        System.out.println("Time: " + time2 + " ms list");


        // Sincronizare la nivel de lista
        // Stergerea la final a tuturor coeficientilor egali cu 0

        DoublyLinkedList linkedList3 = new DoublyLinkedList();
        NodeThread[] threads3 = new NodeThread[noThreads];
        long start3, finish3;
        float time3;

        linkedList3.prepare(maxExp);

        for (int i = 0; i < noThreads; i++) {
            threads3[i] = new NodeThread(noPolynomials, linkedList, false);
        }

        start3 = System.nanoTime();

        for (int i = 0; i < noThreads; i++) {
            threads3[i].start();
        }

        for (int i = 0; i < noThreads; i++) {
            threads3[i].join();
        }

        linkedList3.deleteZeros();

        finish3 = System.nanoTime();

        linkedList3.broke();
        linkedList3.writeInFile("result1.txt");

        time3 = (finish3 - start3) / (float) 1_000_000;
        System.out.println("Time: " + time3 + " ms node");
        System.out.println("Result: " + Utils.isSameContentInFile("result.txt", "result1.txt"));
    }
}
