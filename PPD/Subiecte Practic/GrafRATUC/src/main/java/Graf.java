import com.sun.tools.javac.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graf {
    private List<Muchie> muchii;
    private List<Nod> noduri;

    public Graf() {
        muchii = new ArrayList<>();
        noduri = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        for (int i = 0; i < 100; i++) {
            noduri.add(new Nod(i, i+1));
        }
        this.muchii = Utils.readFromFile("graph_problema1Muchii");
    }

    public boolean verificaNod(int valoare) {
        int i = 0;
        Muchie muchie;
        while (i < muchii.size()) {
            muchie = muchii.get(i);
            if (muchie.getValue().equals(valoare)) return true;
            i++;
        }
        return false;
    }

    public boolean verificaMuchie(int index1, int index2) {
        int i = 0;
        return verificaNod(index1) && verificaNod(index2);
    }

    public void insereazaNod(int index, int value) {
        if (!verificaNod(value)) {
            noduri.add(new Nod(index, value));
        }
    }

    public void insereazaMuchie(int index1, int index2, int value) {
        if (verificaNod(index1) && verificaNod(index2)) {
            muchii.add(new Muchie(index1, index2, value));
        }
    }

    public void updateMuchie(int index1, int index2, int value) {
        if (verificaMuchie(index1, index2)) {
            int i = 0;
            Muchie muchie;
            while (i < muchii.size()) {
                muchie = muchii.get(i);
                if (muchie.getNod_1().equals(index1) && muchie.getNod_2().equals(index2)) {
                    muchii.get(i).setValue(value);
                    return;
                }
                i++;
            }
        }
    }

    public Iterator getIterator() {
        return new MyIterator();
    }

    public void iterate() {
        Iterator iterator = this.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private class MyIterator implements Iterator {
        private int position = 0;

        public boolean hasNext() {
            return position < muchii.size();
        }

        public Muchie next() {
            if (this.hasNext()) {
                return muchii.get(position++);
            } else
                return null;
        }
    }
}
