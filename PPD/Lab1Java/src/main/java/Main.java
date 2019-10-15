import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
//        Utils.createNewFile("num.txt", 2, 500_000, 500_002);

        List<BigNumber> numbers = Utils.readNumbersFromFile("num.txt");
        long start, finish, time;
        BigNumber bigNumber1 = numbers.get(0);
        BigNumber bigNumber2 = numbers.get(1);
        BigNumber bigNumberSum;

        System.out.println("Sequential");
        start = System.nanoTime();
        bigNumberSum = bigNumber1.addSequential(bigNumber2);
        finish = System.nanoTime();
        time = finish - start;
        System.out.println("Time: " + time);
        Utils.writeNumberInFile(bigNumberSum, "sequential.txt");

        long start2 = System.nanoTime();
        bigNumberSum = bigNumber1.addParallel(bigNumber2, 1);
        long finish2 = System.nanoTime();
        long time2 = finish2 - start2;
        System.out.println("Time: " + time2);
        Utils.writeNumberInFile(bigNumberSum, "parallel.txt");

//        boolean sameSum  = Utils.isSameContentInFile("sequential.txt", "parallel.txt");
//        System.out.println("Is same result: "+sameSum);
    }
}
