package Referinte;

public class FormulaHelper {
    private double a,b;

    public FormulaHelper(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public static double patratBinom(double x,double y){
        return Math.pow(x+y,2);
    }

    public static double sqrt(double a){
        return Math.sqrt(a);
    }

    public double suma1(double a,double b){
        return a+b;
    }

    public static double suma(double a,double b){
        return a+b;
    }
}
