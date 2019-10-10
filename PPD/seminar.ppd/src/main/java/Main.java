public class Main {

    public static void main(String[] args) throws Exception {
        int size = 10000;

        Vector vector1 = new Vector(size);
        Vector vector2 = new Vector(size);

        for(int index=0;index<size;index++){
            vector1.elements.set(index, (double) index);
            vector2.elements.set(index, (double) size - index);
        }

        System.out.println("Secvential");
        long start = System.nanoTime();
        Vector suma_sec = vector1.add_sec(vector2);
        long end = System.nanoTime();

//        suma_sec.printElements();
        System.out.println("Time: " );
        System.out.println(end-start);

        System.out.println("Paralel");
        start = System.nanoTime();
        Vector suma_paralel = vector1.add_paralel(vector2, 4);
        end = System.nanoTime();

//        suma_paralel.printElements();
        System.out.println("Time: " );
        System.out.println(end-start);
    }
}
