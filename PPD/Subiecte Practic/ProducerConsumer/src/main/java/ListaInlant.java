import java.util.Iterator;

class MyLinkedList<T> {
    private Node<T> start = null;
    private int size = 0;
    private int capacity;

    public MyLinkedList(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public int getSize() {
        return size;
    }

    public void add(T value) {
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

    public void delete(Node<T> node) {
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

    public Iterator getIterator() {
        return new MyIterator();
    }

    public void iterate() {
        System.out.println("ITERARE:");

        Iterator iterator = this.getIterator();
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            System.out.println(node);
        }
    }

    private class MyIterator implements Iterator {
        private Node crr = start;

        public boolean hasNext() {
            return crr != null;
        }

        public Node next() {
            if (this.hasNext()) {
                Node returned = crr;
                crr = crr.getNext();
                return returned;
            } else
                return null;
        }
    }
}

public class ListaInlant{
    public static void main(String[] args) {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>(5);

        linkedList.add(1);
        linkedList.add(1);
        linkedList.add(1);
    }
}
