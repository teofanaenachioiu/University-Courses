public class Numar {
    private int a;

    public Numar(int a) {
        this.a = a;
    }

    public void aduna(Numar b){
        this.a +=b.getNumber();
    }

    public int getNumber(){
        return this.a;
    }
}
