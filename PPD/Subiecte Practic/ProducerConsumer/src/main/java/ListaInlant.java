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

    public void delete(T value) {
        if (start == null) return;

        if (start.getData().equals(value)) {
            start = start.getNext();
            size--;
            return;
        }

        Node prev = start;
        Node crr = start.getNext();
        while (crr != null && !crr.getData().equals(value)) {
            prev = crr;
            crr = crr.getNext();
        }

        if (crr == null) {
            System.out.println("Node not found. I can't delete!! " + value);
            return;
        }

        prev.setNext(crr.getNext());
        size--;
    }

    public Iterator<T> getIterator() {
        return new MyIterator();
    }

    public void iterate() {
        System.out.println("ITERARE:");

        Iterator<T> iterator = this.getIterator();
        while (iterator.hasNext()) {
            T node = iterator.next();
            System.out.println(node);
        }
    }

    private class MyIterator implements Iterator<T> {
        private Node<T> crr = start;

        public boolean hasNext() {
            return crr != null;
        }

        public T next() {
            if (this.hasNext()) {
                Node<T> returned = crr;
                crr = crr.getNext();
                return returned.getData();
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

        linkedList.iterate();
        linkedList.delete(1);
        linkedList.iterate();

    }
}
