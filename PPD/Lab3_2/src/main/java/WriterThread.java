public class WriterThread extends Thread {
    private DoublyLinkedList linkedList;
    private final ConcurrentLinkedQueue queue;
    private ReaderThread readerThread;

    public WriterThread(ReaderThread readerThread, DoublyLinkedList doublyLinkedList, ConcurrentLinkedQueue concurrentLinkedQueue) {
        linkedList = doublyLinkedList;
        queue = concurrentLinkedQueue;
        this.readerThread = readerThread;
    }

//    @Override
//    public void run() {
//        super.run();
//        while (this.readerThread.isReading()) {
//            synchronized (this.queue) {
//                Monom monom = this.queue.peek();
//                System.out.println("Am citit din coada " + monom);
//                if (monom != null) {
//                    linkedList.insert(monom, true);
//                }
//            }
//
//        }
//    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                synchronized (queue) {

                    this.queue.wait();

                    if (this.readerThread.isReading() || this.queue.getSize()>0 ) {
                        Monom monom = this.queue.peek();
                        if (monom != null) {
                            linkedList.insert(monom, true);
                        }
                    } else {
                        break;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
