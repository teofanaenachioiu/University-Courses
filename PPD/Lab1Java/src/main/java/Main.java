import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
//        int minDigits = 10_000;
//        int maxDigits = 10_100;
//        Utils.createNewFile("num.txt", 2, minDigits, maxDigits);
        int no_threads = 4;

        List<BigNumber> numbers = Utils.readNumbersFromFile("num.txt");
        BigNumber bigNumber1 = numbers.get(0);
        BigNumber bigNumber2 = numbers.get(1);

        long start, finish;
        float time;


//        start = System.nanoTime();
//        bigNumber1.multiplySequentially(bigNumber2);
//        finish = System.nanoTime();
//        time = (finish - start) / (float) 1_000_000;
//        System.out.println("Time: " + time + " sequential");


        long start1, finish1;
        float time1;

        start1 = System.nanoTime();
        bigNumber1.multiplyParallel(bigNumber2, no_threads);
        finish1 = System.nanoTime();
        time1 = (finish1 - start1) / (float) 1_000_000;
        System.out.println("Time: " + time1 + " parallel");
    }
}
