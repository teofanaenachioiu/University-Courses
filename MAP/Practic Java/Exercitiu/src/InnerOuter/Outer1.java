package InnerOuter;

//Clasa interna normala private
//Calsa inner implementeaza interfata Hidden

public class Outer1 {

    public Hidden getInnerInstance(){
        return new HiddenInner(5);
    }

    private class HiddenInner implements Hidden{
        private int i;
        private HiddenInner(int i){
            this.i=i;
        }

        @Override
        public int value() {
            return this.i;
        }
    }
}
