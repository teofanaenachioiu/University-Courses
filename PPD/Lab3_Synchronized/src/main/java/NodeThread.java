import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeThread extends Thread {
    private static AtomicInteger noPolynomials;
    private final DoublyLinkedList linkedList;
    private boolean deleteImmediately;

    NodeThread(int noPolynomials, DoublyLinkedList linkedList, boolean deleteImmediately) {
        this.linkedList = linkedList;
        NodeThread.noPolynomials = new AtomicInteger(noPolynomials);
        this.deleteImmediately = deleteImmediately;
    }

    @Override
    public void run() {
        super.run();
        int noFile;
        while ((noFile = noPolynomials.getAndDecrement()) > 0) {
            File file = new File(Objects.requireNonNull(Main.class.getClassLoader()
                    .getResource("polinom" + noFile + ".txt")).getFile());
            Scanner sc;
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] elem = line.split(",");
                    Monom monom = new Monom(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]));
                    linkedList.insertSync(monom, deleteImmediately);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
