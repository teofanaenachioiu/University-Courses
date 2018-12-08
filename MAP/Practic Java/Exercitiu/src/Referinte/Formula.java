package Referinte;

@FunctionalInterface
public interface Formula {
    double pi=3.14;

    double calculate(double a, double b);

//    default double sqrt(double a){
//        return Math.sqrt(a);
//    }
//
//    default double power(double a, double b){
//        return Math.pow(a,b);
//    }
//
//    default double patratBinom(double x, double y){
//        return Math.pow(x+y,2);
//    }
//
//    static double suma(double x,double y){
//        return x+y;
//    }
}
