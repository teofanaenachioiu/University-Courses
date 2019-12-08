import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DoublyLinkedList {
    private Node start;
    private int size;

    public DoublyLinkedList() {
        start = null;
        size = 0;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public int getSize() {
        return size;
    }

    public synchronized void insert(int val) {
        Node newNode = new Node(val, null, null);
        if (start == null)
            start = newNode;
        else {
            Node crr = start;
            while (crr.getLinkNext() != null) {
                crr = crr.getLinkNext();
            }
            crr.setLinkNext(newNode);
            newNode.setLinkPrev(crr);
        }
        size++;
    }

    private synchronized void delete(int data) {
        if (start == null) return;

        Node ptr = start;
        while (ptr != null && ptr.getData() != data) {
            ptr = ptr.getLinkNext();
        }

        if (ptr == null) {
            System.out.println("Node not found. I can't delete!! " + data);
            return;
        }

        Node p = ptr.getLinkPrev();
        Node n = ptr.getLinkNext();

        p.setLinkNext(n);
        if (n != null) {
            n.setLinkPrev(p);
        }
        size--;
    }

    public void display() {
        System.out.print("Doubly Linked List = ");
        if (size == 0) {
            System.out.print("empty\n");
            return;
        }
        if (start.getLinkNext() == null) {
            System.out.println(start.getData());
            return;
        }
        Node ptr;
        System.out.print(start.getData() + "\n");
        ptr = start.getLinkNext();
        while (ptr.getLinkNext() != null) {
            System.out.print(ptr.getData() + "\n");
            ptr = ptr.getLinkNext();
        }
        System.out.print(ptr.getData() + "\n");
    }

    void writeInFile(String filename, Interval interval) throws IOException {
        if (size != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            StringBuilder str = new StringBuilder();

            if (start.getLinkNext() == null) {
                if (interval.getA() <= start.getData() && start.getData() < interval.getB()) {
                    str.append(start.getData());
                }
            } else {
                Node ptr;
                if (interval.getA() <= start.getData() && start.getData() < interval.getB()) {
                    str.append(start.getData()).append("\n");
                }
                ptr = start.getLinkNext();
                while (ptr.getLinkNext() != null) {
                    if (interval.getA() <= ptr.getData() && ptr.getData() < interval.getB()) {
                        str.append(ptr.getData()).append("\n");
                    }
                    ptr = ptr.getLinkNext();
                }
                if (interval.getA() <= ptr.getData() && ptr.getData() < interval.getB()) {
                    str.append(ptr.getData());
                }
            }
            writer.write(str.toString());
            writer.close();
        }
    }

    public void stergeMultipli(int bruiaj) {
        Node crr, prev, next;
        synchronized (start) {
            if (start == null) return;
            crr = start;
        }
        synchronized (crr) {
            prev = crr.getLinkPrev();
            next = crr.getLinkNext();
            if (prev != null) {
                synchronized (prev) {
                    if (next != null) {
                        synchronized (next) {
                            doStuff(crr, next, prev, bruiaj);
                        }
                    } else {
                        doStuff(crr, next, prev, bruiaj);
                    }
                }
            } else if (next != null) {
                doStuff(crr, next, prev, bruiaj);
            } else {
                doStuff(crr, next, prev, bruiaj);
            }
        }
    }

    private void doStuff(Node crr, Node next, Node prev, int bruiaj) {
        while (crr != null) {
            if (crr.getData() % bruiaj == 0) {
                if (prev != null) {
                    prev.setLinkNext(next);
                } else {
                    start = next;
                }
                if (next != null) {
                    next.setLinkPrev(prev);
                }
                size--;
            }
            crr = crr.getLinkNext();
        }
    }
}