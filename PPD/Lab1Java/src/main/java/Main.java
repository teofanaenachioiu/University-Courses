import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
//        Utils.createNewFile("num.txt", 2, 100_000, 100_002);

        List<BigNumber> numbers = Utils.readNumbersFromFile("num.txt");
        long start, finish, time;
        BigNumber bigNumber1 = numbers.get(0);
        BigNumber bigNumber2 = numbers.get(1);
        BigNumber sumNumber;

        System.out.println("Sequential");
        start = System.nanoTime();
        bigNumber1.addSequential(bigNumber2);
        finish = System.nanoTime();
        time = finish - start;
        System.out.println("Time: " + time);
//        sumNumber.printNumber();

        long start1 = System.nanoTime();
//        sumNumber = bigNumber1.addParallel(bigNumber2,4);
        bigNumber1.addParallel(bigNumber2,4);
        long finish1 = System.nanoTime();
        long time1 = finish1 - start1;
        System.out.println("Time: " + time1);
        System.out.println("Parallel");
//        sumNumber.printNumber();
    }
}
