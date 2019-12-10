import java.util.Iterator;

public class MyLinkedList {
    private Node start;
    private int size;
    private boolean newData;

    public int getNumarLocuriOcupate() {
        return numarLocuriOcupate;
    }

    public void setNumarLocuriOcupate(int numarLocuriOcupate) {
        this.numarLocuriOcupate = numarLocuriOcupate;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }

    private int numarLocuriOcupate; // numar locuri ocupate
    private int numarLocuri; // numar locuri de parcare

    public MyLinkedList(int n, int p) {
        this.start = null;
        this.size = 0;
        this.newData = true;
        this.numarLocuriOcupate = 0;
        this.numarLocuri = p;
        initialize(n);
    }

    private void initialize(int n) {
        for (int i = 0; i < n; i++) {
            this.insert(TransactionType.INTRARE);
        }
    }

    public boolean isEmpty() {
        return start == null;
    }

    public int getSize() {
        return size;
    }

    public void insert(TransactionType transactionType) {
        Node newNode = new Node(transactionType, null);
        if (start == null)
            start = newNode;
        else {
            Node crr = start;
            while (crr.getNext() != null) {
                crr = crr.getNext();
            }
            crr.setNext(newNode);
        }
        size++;
        if (transactionType.equals(TransactionType.INTRARE)) {
            newData = true;
            numarLocuriOcupate++;
        } else {
            numarLocuriOcupate--;
        }
    }

    public boolean isNewData() {
        return this.newData;
    }

    public Iterator getIterator() {
        newData = false;
        return new MyIterator();
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
