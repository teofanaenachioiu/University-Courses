public class Main {
    public static void main(String[] args) throws InterruptedException {

        int p = 32; //numar threaduri
        int n = 50; //nr numere

        Numar[] numere = Utils.generateNumber(n);

        int sumaCorecta = Utils.addSeq(numere, n);
        System.out.println(sumaCorecta );

        AddThread[] threads = new AddThread[p];
        int k = (int) (Math.log(p) / Math.log(2));

        for (int i = 0; i < p; i++) {
            threads[i] = new AddThread(i, numere, p, k, threads);
        }
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            threads[i].join();
        }
        System.out.println(numere[0].getNumber());
    }
}
