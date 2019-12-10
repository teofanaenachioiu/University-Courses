public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 51;
        int p = 11;
        int c = 5;

        BandaTransportoare banda = new BandaTransportoare(n);

        Producator[] producatori = new Producator[p];
        Consumator[] consumatori = new Consumator[c];

        for (int i = 0; i < p; i++) {
            producatori[i] = new Producator(banda);
        }
        for (int i = 0; i < c; i++) {
            consumatori[i] = new Consumator(banda);
        }

        for (int i = 0; i < p; i++) {
            producatori[i].start();
        }
        for (int i = 0; i < c; i++) {
            consumatori[i].start();
        }

        for (int i = 0; i < p; i++) {
            producatori[i].join();
        }
        for (int i = 0; i < c; i++) {
            consumatori[i].join();
        }
    }
}
