import static java.lang.Math.sqrt;

public class Poligon {
    NumarComplex [] varfuri;

    public Poligon() {
        varfuri=null;
    }

    public Poligon(NumarComplex [] nrc) {
        varfuri=nrc;
    }

    private double distanta(NumarComplex n1,NumarComplex n2){
        double x1=n1.getReal();
        double y1=n1.getImg();
        double x2=n2.getReal();
        double y2=n2.getImg();
        return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }


    public double perimetru(){
        double p=0;
        if(varfuri.length<=2) return 0;
        for(int i=1;i<varfuri.length;i++){
            double d=distanta(varfuri[i-1],varfuri[i]);
            p=p+ d;
        }
        double d=distanta(varfuri[varfuri.length-1],varfuri[0]);
        p=p+distanta(varfuri[varfuri.length-1],varfuri[0]);
        return p;
    }


}