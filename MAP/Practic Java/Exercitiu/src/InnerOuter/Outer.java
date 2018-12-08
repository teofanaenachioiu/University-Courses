package InnerOuter;

//Clasa interna normala public

public class Outer {
    private int i=1;
    public int getValue(){
        return this.i;
    }
    public class Inner{
        private int k;
        public Inner(int i){
            this.k=Outer.this.i+1; //Va afisa 2 pentru out.new Inner(3)
            //this.k=i+1; //Va afisa 4 pentru out.new Inner(3)
        }
        public int getValue(){
            return this.k;
        }
    }
}

