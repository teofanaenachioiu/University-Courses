package sem5;

import java.util.Arrays;
import java.util.List;

public class Sem5_Test {

    public static <E> void  printArie(List<E> l, Area<E> f) {
        l.forEach(x-> System.out.println(f.compute(x)));
    }

    public static void main(String[] args) {
        Area<Cerc> cercArea = (Cerc x) -> (float) (Math.PI*x.getRaza()*x.getRaza());


        Cerc c = new Cerc(1);
        float ariaCerc = cercArea.compute(c);
//        System.out.println(ariaCerc);


        Area<Patrat> patratArea = x -> x.getLatura()*x.getLatura();

        Patrat p = new Patrat(3);
        float ariaPatrat = patratArea.compute(p);
//        System.out.println(ariaPatrat);

        List<Cerc> listCerc = Arrays.asList(c,new Cerc(2));
        printArie(listCerc,cercArea);
    }
}

class Cerc{
    public float getRaza() {
        return raza;
    }

    public void setRaza(float raza) {
        this.raza = raza;
    }

    public Cerc(float raza) {
        this.raza = raza;
    }

    private float raza;

}
class Patrat{
    public float getLatura() {
        return latura;
    }

    public void setLatura(float latura) {
        this.latura = latura;
    }

    public Patrat(float latura) {
        this.latura = latura;
    }

    private float latura;
}