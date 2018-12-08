package Referinte;

public class Main {
    public static void main(String[] args) {

        //Referinta la metoda de clasa
        Formula bin3=FormulaHelper::patratBinom;
        System.out.println(bin3.calculate(2,3));

        //Referinta la o metoda de instanta
        FormulaHelper helper=new FormulaHelper(5,6);
        Formula formula=helper::suma1;
        System.out.println(formula.calculate(25,64));

        //Referinta la o metoda de clasa
        Flyable<Boeing> f=Boeing::getHeight;
        System.out.println(f.canFly(new Boeing(23)));

        //Functii lambda
        double a=2.1, b=1.9;
        Formula formula1=(x,y)->{return FormulaHelper.suma(x,y);};
        System.out.println(formula1.calculate(a,b));

        FormulaHelper helper1=new FormulaHelper(a,b);
        Formula formula2=(x,y)->{return helper1.suma(x,y);};
        System.out.println(formula2.calculate(a,b));
    }
}
