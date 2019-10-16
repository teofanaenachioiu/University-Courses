import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
//        Utils.createNewFile("num.txt", 2, 100_000, 100_002);

        List<BigNumber> numbers = Utils.readNumbersFromFile("num.txt");
        long start, finish, time;
        BigNumber bigNumber1 = numbers.get(0);
        BigNumber bigNumber2 = numbers.get(1);
        BigNumber bigNumberSum;

//        System.out.println("Sequential");
        start = System.nanoTime();
        bigNumber1.addSequential(bigNumber2);
        finish = System.nanoTime();
        time = finish - start;
        System.out.println("Time: " + time + " secvential");
//        Utils.writeNumberInFile(bigNumberSum, "sequential.txt");

        int no_threads = 2;

        long start2 = System.nanoTime();
        bigNumberSum = bigNumber1.addParallel(bigNumber2, no_threads);
        long finish2 = System.nanoTime();
        long time2 = finish2 - start2;
        System.out.println("Time: " + time2 + " paralel 1");
        Utils.writeNumberInFile(bigNumberSum, "parallelOpt.txt");

        long start3 = System.nanoTime();
        bigNumberSum = bigNumber1.addParallel22(bigNumber2, no_threads);
        long finish3 = System.nanoTime();
        long time3 = finish3 - start3;
        System.out.println("Time: " + time3 + " paralel 2");
        Utils.writeNumberInFile(bigNumberSum, "parallelNeopt.txt");
        boolean isSame = Utils.isSameContentInFile("parallelOpt.txt", "parallelNeopt.txt");
        System.out.println(isSame);
    }
}
