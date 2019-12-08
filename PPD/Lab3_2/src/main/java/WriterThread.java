package threads;

import collections.ConcurrentLinkedQueue;
import collections.DoublyLinkedList;
import collections.Monom;

public class WriterThread extends Thread{
    private DoublyLinkedList linkedList;
    private ConcurrentLinkedQueue queue;

    public WriterThread(DoublyLinkedList doublyLinkedList, ConcurrentLinkedQueue concurrentLinkedQueue){
        linkedList = doublyLinkedList;
        queue = concurrentLinkedQueue;
    }
    @Override
    public void run() {
        super.run();

        while(!this.queue.isEmpty()){
            Monom monom = this.queue.peek();
            linkedList.insert(monom, true);
        }

    }
}
