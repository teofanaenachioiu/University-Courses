package exemple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class MainEx {
    public static void main(String[] args) {
        // Now , you trick your mom into creating you a promise of eventual
// donation, she gives you that promise object, but she is not really
// in rush to fulfill it yet:
        Supplier<Integer> momsPurse = () -> {

            try {
                Thread.sleep(1000);//mom is busy
            } catch (InterruptedException ignored) {
                ;
            }

            return 100;

        };


        ExecutorService ex = Executors.newFixedThreadPool(10);

        CompletableFuture<Integer> promise =
                CompletableFuture.supplyAsync(momsPurse, ex);

// You are happy, you run to thank you your mom:
        promise.thenAccept(u -> System.out.println("Thank you mom for $" + u));

// But your father interferes and generally aborts mom's plans and
// completes the promise (sets its value!) with far lesser contribution,
// as fathers do, very resolutely, while mom is slowly opening her purse
// (remember the Thread.sleep(...)) :
        promise.complete(10);
    }
}
