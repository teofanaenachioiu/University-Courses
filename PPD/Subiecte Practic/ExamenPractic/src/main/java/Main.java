import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 1;

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Dati numarul de persoane: ");
        n = keyboard.nextInt();

        ContBancar contBancar = new ContBancar();

        ThreadPersoana[] threads = new ThreadPersoana[n];
        ThreadOk[] threadsOk = new ThreadOk[n];
        AtomicInteger okeyuri = new AtomicInteger(0);

        for (int i = 0; i < n; i++) {
            threads[i] = new ThreadPersoana(contBancar, okeyuri, i, n);
            threadsOk[i] = new ThreadOk(contBancar, okeyuri, n);
        }
        ThreadPrint threadPrint = new ThreadPrint(contBancar);

        for (int i = 0; i < n; i++) {
            threads[i].start();
            threadsOk[i].start();
        }
        threadPrint.start();

        for (int i = 0; i < n; i++) {
            threads[i].join();
            threadsOk[i].join();
        }
        threadPrint.join();

        System.out.println("GATA");
    }
}
