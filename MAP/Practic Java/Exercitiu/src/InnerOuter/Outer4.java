package InnerOuter;

//Clasa interna anonima

public class Outer4 {
    public Hidden getInnerInstance(int i){
        return new Hidden(){
            private int k=i;
            @Override
            public int value() {
                return this.k;
            }
        };
    }
}
