import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

class MyLinkedList<T> {
    private Node<T> start = null;
    private int size = 0;

    public MyLinkedList() {
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

    public void scrieInFisier() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File("out.txt"), false));
            Iterator<T> iterator = this.getIterator();
            while (iterator.hasNext()) {
                T node = iterator.next();
                br.append(node.toString()).append('\n');
            }
            br.newLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ITERARE");
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