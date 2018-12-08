package InnerOuter;

public class Main {
    public static void main(String[] args) {
        //Inner public (+ protected, la nivel de packet)
//        Outer out=new Outer();
//        Outer.Inner in=out.new Inner(3);
//        System.out.println(in.getValue());

        //Inner private
//        Outer1 out1=new Outer1();
//        Hidden in1=out1.getInnerInstance();
//        System.out.println(in1.value());

        //Clasa interna in metoda
//        Outer2 out2=new Outer2();
//        System.out.println(out2.getInnerInstance().value());

        //Clasa interna in bloc
//        Outer3 out3=new Outer3();
//        Hidden in=out3.getInnerInstance(11);
//        System.out.println(in.value());

        //Clasa interna anonima
//        Outer4 out4=new Outer4();
//        System.out.println(out4.getInnerInstance(5).value());

        //Clase interne statice
        Outer5 out5=new Outer5();
        System.out.println(out5.new NonStaticInner().value());
        System.out.println(new Outer5.StaticInner().value());
    }
}
