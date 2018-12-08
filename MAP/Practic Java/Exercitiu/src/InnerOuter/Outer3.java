package InnerOuter;

//Clasa interna in bloc

public class Outer3 {
    public Hidden getInnerInstance(int i){
        if(i==11){
            class BlockInner implements Hidden{
                private int i=11;
                @Override
                public int value() {
                    return this.i;
                }
            }
            return new BlockInner();
        }
        return null;
    }
}
