import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {
    public int size;
    public List<Double> elements;

    public Vector(int size, List<Double> vector) {
        this.size = size;
        this.elements = vector;
    }

    public Vector(int size) {
        this.size = size;
        this.elements = Arrays.asList(new Double[size]);
    }

    public void printElements() {
        this.elements.forEach(System.out::println);
    }

    public Vector add_sec(Vector vector1) throws Exception {
        if (vector1.size != this.size) {
            throw new Exception("wrong");
        }

        Vector suma = new Vector(this.size);

        for (int index = 0; index < this.size; index++) {
            suma.elements.set(index, Math.sqrt(this.elements.get(index)) + Math.sqrt(vector1.elements.get(index)));
        }
        return suma;
    }

    public Vector add_paralel(Vector vector1, int nr_threads) throws Exception {
        if (vector1.size != this.size) {
            throw new Exception("wrong");
        }

        Vector suma = new Vector(this.size);

        int start = 0;
        int dim = suma.size / nr_threads;
        int end = suma.size / nr_threads;
        int rest = suma.size % nr_threads;

        AddThread[] threads = new AddThread[nr_threads];

        for (int index = 0; index < nr_threads; index++) {
            if (rest > 0) {
                end++;
                rest--;
            }
            threads[index] = new AddThread(start, end, this, vector1, suma);
            threads[index].start();

            start = end;
            end = end + dim;
        }

        for (int index = 0; index < nr_threads; index++) {
            threads[index].join();
        }

        return suma;
    }
}
