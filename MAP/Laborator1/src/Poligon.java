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
        int x1=n1.getReal();
        int y1=n1.getImg();
        int x2=n2.getReal();
        int y2=n2.getImg();
        return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }


    public double perimetru(){
        double p=0;
        for(int i=1;i<varfuri.length;i++){
            p=p+ distanta(varfuri[i-1],varfuri[i]);
        }
        p=p+distanta(varfuri[varfuri.length-1],varfuri[0]);
        return p;
    }


}
