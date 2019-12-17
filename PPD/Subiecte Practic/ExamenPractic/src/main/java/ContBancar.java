public class ContBancar {
    private MyLinkedList<Tranzactie> lista;
    private Integer soldRon;
    private Integer soldEur;
    private Integer soldDol;

    public ContBancar() {
        lista = new MyLinkedList<Tranzactie>();
        soldRon = 0;
        soldEur = 0;
        soldDol = 0;
    }

    public void depuneRon(int suma) {
        synchronized (soldRon) {
            soldRon += suma;

            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.RON, TipTranzactie.DEPUNE, suma, soldRon));
            }
        }
    }

    public void depuneEuro(int suma) {
        synchronized (soldEur) {
            soldEur += suma;

            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.EURO, TipTranzactie.DEPUNE, suma, soldEur));
            }
        }
    }

    public  void depuneDolar(int suma) {
        synchronized (soldDol) {
            soldDol += suma;
            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.DOLAR, TipTranzactie.DEPUNE, suma, soldDol));
            }
        }
    }

    public  void retrageRon(int suma) {
        synchronized (soldRon) {
            soldRon -= suma;

            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.RON, TipTranzactie.RETRAGE, suma, soldRon));
            }
        }
    }

    public  void retrageEuro(int suma) {
        synchronized (soldRon) {
            soldEur -= suma;

            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.EURO, TipTranzactie.RETRAGE, suma, soldEur));
            }
        }
    }

    public  void retrageDolar(int suma) {
        synchronized (soldDol) {
            soldDol -= suma;

            synchronized (lista) {
                lista.add(new Tranzactie(TipValuta.DOLAR, TipTranzactie.RETRAGE, suma, soldDol));
            }
        }
    }

    public synchronized void scrieInFisier() {
        lista.scrieInFisier();
    }
}
