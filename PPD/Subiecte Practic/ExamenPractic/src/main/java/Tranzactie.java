public class Tranzactie {
    private TipValuta tipValuta;
    private TipTranzactie tipTranzactie;
    private int suma;
    private int soldCurent;

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tipValuta=" + tipValuta +
                ", tipTranzactie=" + tipTranzactie +
                ", suma=" + suma +
                ", soldCurent=" + soldCurent +
                '}';
    }

    public Tranzactie(TipValuta tipValuta, TipTranzactie tipTranzactie, int suma, int soldCurent) {
        this.tipValuta = tipValuta;
        this.tipTranzactie = tipTranzactie;
        this.suma = suma;
        this.soldCurent = soldCurent;
    }

    public TipValuta getTipValuta() {
        return tipValuta;
    }

    public void setTipValuta(TipValuta tipValuta) {
        this.tipValuta = tipValuta;
    }

    public TipTranzactie getTipTranzactie() {
        return tipTranzactie;
    }

    public void setTipTranzactie(TipTranzactie tipTranzactie) {
        this.tipTranzactie = tipTranzactie;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public int getSoldCurent() {
        return soldCurent;
    }

    public void setSoldCurent(int soldCurent) {
        this.soldCurent = soldCurent;
    }
}
