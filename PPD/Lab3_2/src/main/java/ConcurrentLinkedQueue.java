import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConcurrentLinkedQueue {
    private Node start;
    private int size;
    private boolean stillReading;

    public ConcurrentLinkedQueue() {
        start = null;
        size = 0;
    }

    public  boolean isEmpty() {
        return start == null;
    }

    public  int getSize() {
        return size;
    }

    public  void add(Monom val) {
        Node newNode = new Node(val, null, null);
        Node currentNode;
        if (start == null) {
            start = newNode;
        } else {
            currentNode = start;
            while (currentNode.getLinkNext() != null) {
                currentNode = currentNode.getLinkNext();
            }
            currentNode.setLinkNext(newNode);
            newNode.setLinkPrev(currentNode);
        }
        size++;
    }

    public  Monom peek(){
        if(start==null) return null;

        Monom returned = start.getData();
        delete(start);
        return returned;
    }

    private void delete(Node node) {
        if (start == node) {
            start = start.getLinkNext();
            if(start!=null){
                start.setLinkPrev(null);
            }
        } else {
            Node ptr = start.getLinkNext();
            while (ptr != node) {
                ptr = ptr.getLinkNext();
            }

            if (ptr == null) {
                System.out.println("Node not found. I can't delete!! " + node.getData());
                return;
            }

            Node p = ptr.getLinkPrev();
            Node n = ptr.getLinkNext();

            p.setLinkNext(n);
            if (n != null) {
                n.setLinkPrev(p);
            }
        }
        size--;
    }

    public void display() {
        System.out.print("Queue = ");
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

    void writeInFile(String filename) throws IOException {
        if (size != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            StringBuilder str = new StringBuilder();

            if (start.getLinkNext() == null) {
                str.append(start.getData());
            } else {
                Node ptr;
                str.append(start.getData()).append("\n");
                ptr = start.getLinkNext();
                while (ptr.getLinkNext() != null) {
                    str.append(ptr.getData()).append("\n");
                    ptr = ptr.getLinkNext();
                }
                str.append(ptr.getData());

            }
            writer.write(str.toString());
            writer.close();
        }
    }

    void deleteZeros() {
        Node n = start;
        while (n != null) {
            if (n.getData().getCoefficient() == 0) {
                delete(n);
            }
            n = n.getLinkNext();
        }
    }

}
