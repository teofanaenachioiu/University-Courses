package InnerOuter;

//Clase interne in metode

public class Outer2 {
    public Hidden getInnerInstance(){
        final int a=9;
        class MethodHidden implements Hidden{
            private int i=11;

            @Override
            public int value() {
                this.i+=a;
                return this.i;
            }
        }
        return new MethodHidden();
    }
}

