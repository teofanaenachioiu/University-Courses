public class MyQueue<T> {
    private Node<T> start = null;
    private int size = 0;
    private int capacity = 5;

    private boolean isEmpty() {
        return start == null;
    }

    private int getSize() {
        return size;
    }

    private void add(T value) {
        Node<T> newNode = new Node<T>(value, null);

        if (start == null) {
            start = newNode;
        } else {
            Node<T> currentNode = start;
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(newNode);
        }
        size++;
    }

    private T peek() {
        if (start == null) return null;

        T returned = start.getData();
        delete(start);
        return returned;
    }

    private void delete(Node<T> node) {
        if (start == null) return;

        if (start == node) {
            start = start.getNext();
            size--;
            return;
        }

        Node prev = start;
        Node crr = start.getNext();
        while (crr != null && crr != node) {
            prev = crr;
            crr = crr.getNext();
        }

        if (crr == null) {
            System.out.println("Node not found. I can't delete!! " + node.getData());
            return;
        }

        prev.setNext(crr.getNext());
        size--;
    }

    public void display() {
        System.out.print("Queue = ");
        if (size == 0) {
            System.out.print("empty\n");
            return;
        }
        if (start.getNext() == null) {
            System.out.println(start.getData());
            return;
        }
        Node ptr;
        System.out.print(start.getData() + "\n");
        ptr = start.getNext();
        while (ptr.getNext() != null) {
            System.out.print(ptr.getData() + "\n");
            ptr = ptr.getNext();
        }
        System.out.print(ptr.getData() + "\n");
    }

    public synchronized void produce(T val) throws InterruptedException {
        while (size == capacity) {
            System.out.println("Astept sa se consume...");
            wait();
        }
        add(val);
        notifyAll();
    }

    public synchronized T consume() throws InterruptedException {
        while (size == 0) {
            System.out.println("Astept sa se produca...");
            wait();
        }
        T val = peek();
        notifyAll();
        return val;
    }
}
