import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DictionarN2 {
    private Node<String, List<Integer>> start;

    public boolean verifyKey(String key) {
        Node crr = start;
        while (crr != null && !crr.getKey().equals(key)) {
            crr = crr.getNext();
        }
        return crr != null;
    }

    public void insertKey(String key) {
        if (!verifyKey(key)) {
            Node<String, List<Integer>> newNode = new Node<>(key, new ArrayList<>());

            // dictionarul era vid
            // inserez pe prima pozitie
            if (start == null) {
                start = newNode;
                return;
            }

            // dictionarul nu e vid
            // caut pozitia de inserat
            // inserez intre nodul curent si nodul next
            Node<String, List<Integer>> crr = start;
            Node<String, List<Integer>> next = start.getNext();
            while (next != null && next.getKey().compareTo(key) < 0) {
                crr = next;
                next = next.getNext();
            }
            newNode.setNext(next);
            crr.setNext(newNode);
        }
    }

    public void pushValue(String key, Integer nota) {
        synchronized (key) {
            if (verifyKey(key)) {
                Node<String, List<Integer>> crr = start;
                while (crr != null && !crr.getKey().equals(key)) {
                    crr = crr.getNext();
                }
                if (crr != null) {
                    List<Integer> note = crr.getValue();
                    note.add(nota);
                }
            }
        }
    }

    public void deleteValue(String key, Integer nota) {
        synchronized (key) {
            if (verifyKey(key)) {
                // verific daca trebuie sa sterg din nodul de start
                if (start.getKey().equals(key)) {
                    List<Integer> note = start.getValue();
                    note.remove(nota);
                    return;
                }

                // caut pozitia de sters
                // am cel putin doua noduri
                Node<String, List<Integer>> prev = start;
                Node<String, List<Integer>> crr = start.getNext();
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


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("DictionarN2{");

        Node<String, List<Integer>> crr = start;
        while (crr != null) {
            float media = Utils.computeAverage(crr.getValue());
            ret.append("{ Key: ").append(crr.getKey()).append(", Value: ").append(media).append("} ");
            crr = crr.getNext();
        }

        ret.append("}");
        return String.valueOf(ret);
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