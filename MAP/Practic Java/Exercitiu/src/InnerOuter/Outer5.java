package InnerOuter;

//Clasa interna statica

public class Outer5 {
    public int outerMember=9;
    public class NonStaticInner{
        private int i=1;
        public int value(){
            return i+Outer5.this.outerMember;
            //intr-o clasa nestatica putem accesa un membru nestatic
        }
    }
    public static class StaticInner{
        private int i=99;
        public int value(){
            return this.i;
        }
    }
}
