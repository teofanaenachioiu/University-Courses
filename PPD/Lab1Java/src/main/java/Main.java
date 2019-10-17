import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Utils.createNewFile("num.txt", 2, 500_000, 500_002);
        int no_threads = 8;

        List<BigNumber> numbers = Utils.readNumbersFromFile("num.txt");
        long start, finish, time;
        BigNumber bigNumber1 = numbers.get(0);
        BigNumber bigNumber2 = numbers.get(1);
        BigNumber bigNumberSum;

        start = System.nanoTime();
        bigNumber1.addSequential(bigNumber2);
        finish = System.nanoTime();
        time = finish - start;
        System.out.println("Time: " + time + " secvential");

//        Utils.writeNumberInFile(bigNumberSum, "sequential.txt");


        long start2 = System.nanoTime();
//        bigNumberSum = bigNumber1.addParallel(bigNumber2, no_threads);
        bigNumber1.addParallel(bigNumber2, no_threads);
        long finish2 = System.nanoTime();
        long time2 = finish2 - start2;
        System.out.println("Time: " + time2 + " paralel 1");

//        Utils.writeNumberInFile(bigNumberSum, "parallelOpt.txt");

        long start3 = System.nanoTime();
//        bigNumberSum = bigNumber1.addParallel22(bigNumber2, no_threads);
        bigNumber1.addParallel22(bigNumber2, no_threads);
        long finish3 = System.nanoTime();
        long time3 = finish3 - start3;
        System.out.println("Time: " + time3 + " paralel 2");

//        Utils.writeNumberInFile(bigNumberSum, "parallelNeopt.txt");

//        boolean isSame = Utils.isSameContentInFile("parallelOpt.txt", "parallelNeopt.txt");
//        System.out.println(isSame);
    }
}
