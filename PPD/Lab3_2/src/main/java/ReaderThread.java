import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class ReaderThread extends Thread{
    private ConcurrentLinkedQueue queue;
    private int noPolynomials;
    private boolean isReading;

    public ReaderThread(ConcurrentLinkedQueue queue, int noPolynomials){
        this.queue = queue;
        this.noPolynomials = noPolynomials;
        this.isReading = true;
    }

//    @Override
//    public void run() {
//        super.run();
//
//        int nr = 1;
//        while (nr <= noPolynomials) {
//            File file = new File(Objects.requireNonNull(Main.class.getClassLoader()
//                    .getResource("polinom" + nr + ".txt")).getFile());
//            Scanner sc;
//            try {
//                sc = new Scanner(file);
//                while (sc.hasNextLine()) {
//                    String line = sc.nextLine();
//                    String[] elem = line.split(",");
//                    Monom monom = new Monom(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]));
//                    queue.add(monom);
//                    System.out.println("Am adaugat "+ monom);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            finally {
//                nr++;
//            }
//        }
//        isReading = false;
//    }

    public boolean isReading() {
        return isReading;
    }



    @Override
    public void run() {
        super.run();

        int nr = 1;
        while (nr <= noPolynomials) {
            File file = new File(Objects.requireNonNull(Main.class.getClassLoader()
                    .getResource("polinom" + nr + ".txt")).getFile());
            Scanner sc;
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] elem = line.split(",");
                    Monom monom = new Monom(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]));
                    synchronized (queue){
                        queue.add(monom);
                        this.queue.notify();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                nr++;
            }
        }
        isReading = false;

        while(true){
            synchronized (queue){
                if(queue.getSize()>0){
                    queue.notify();
                }
                else{
                    break;
                }
            }
        }

        synchronized (queue){
            queue.notifyAll();
        }
    }
}
