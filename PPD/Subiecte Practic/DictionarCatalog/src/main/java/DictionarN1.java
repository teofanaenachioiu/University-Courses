import java.util.Iterator;

public class DictionarN1 {
    private Node<String, DictionarN2> start;

    public synchronized boolean verifyKey(String key) {
        Node crr = start;
        while (crr != null && !crr.getKey().equals(key)) {
            crr = crr.getNext();
        }
        return crr != null;
    }

    public void insertKey(String key) {
        if (!verifyKey(key)) {
            Node<String, DictionarN2> newNode = new Node<>(key, new DictionarN2());

            // dictionarul era vid
            // inserez pe prima pozitie
            if (start == null) {
                start = newNode;
                return;
            }

            if (start.getKey().compareTo(key) > 0) {
                newNode.setNext(start);
                start = newNode;
                return;
            }

            // dictionarul nu e vid
            // caut pozitia de inserat
            // inserez intre nodul curent si nodul next
            Node<String, DictionarN2> crr = start;
            Node<String, DictionarN2> next = start.getNext();
            while (next != null && next.getKey().compareTo(key) < 0) {
                crr = next;
                next = next.getNext();
            }
            newNode.setNext(next);
            crr.setNext(newNode);
        }
    }

    public void pushValue(String nume_student, Integer nota) {
        String key = nume_student.substring(0, 1).toUpperCase();
        if (!verifyKey(key)) {
            insertKey(key);
        }
        Node<String, DictionarN2> crr = start;
        while (crr != null && !crr.getKey().equals(key)) {
            crr = crr.getNext();
        }
        if (crr != null) {
            synchronized (crr.getKey()) {
                DictionarN2 dictionarN2 = crr.getValue();
                if (!dictionarN2.verifyKey(nume_student)) {
                    dictionarN2.insertKey(nume_student);
                }
                dictionarN2.pushValue(nume_student, nota);
            }
        }
    }

    public void deleteValue(String nume_student) {
        String key = nume_student.substring(0, 1).toUpperCase();
        if (verifyKey(key)) {

            // verific daca trebuie sa sterg din nodul de start
            synchronized (start.getKey()) {
                if (start.getKey().equals(key)) {
                    start = start.getNext();
                    return;
                }
            }

            // caut pozitia de sters
            // am cel putin doua noduri
            Node<String, DictionarN2> crr = start.getNext();
            synchronized (crr.getKey()) {
                Node<String, DictionarN2> prev = start;
                while (crr != null && !crr.getKey().equals(key)) {
                    prev = crr;
                    crr = crr.getNext();
                }
                if (crr != null) {
                    prev.setNext(crr.getNext());
                }
            }
        }
    }

    public Iterator getIterator() {
        return new MyIterator();
    }

    public void iterate() {
        System.out.println("ITERARE DICTIONAR:");
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
