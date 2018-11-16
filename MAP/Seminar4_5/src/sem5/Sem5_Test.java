package sem5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Cerc{
    int raza;

    public int getRaza() {
        return raza;
    }

    public void setRaza(int raza) {
        this.raza = raza;
    }

    public Cerc(int raza) {
        this.raza = raza;
    }
}

class Patrat{
    int latura;

    public Patrat(int latura) {
        this.latura = latura;
    }

    public int getLatura() {
        return latura;
    }

    public void setLatura(int latura) {
        this.latura = latura;
    }
}

class AreaHelper{
    static public float computeCircleArea(Cerc c){
        return (float)(c.getRaza() * c.getRaza() * Math.PI);
    }

}

public class Sem5_Test {

    //metoda generica definita intr-o clasa
    public static <E>void printArie(List<E> l, Area<E> f){
        l.forEach(x-> System.out.println(f.compute(x)));
    }



    public static void main(String[] args) {
        //Soblon de proiectare???

        //Metoda cu functii lambda
        Area<Cerc> cercArea = (Cerc c) -> {return (float)(c.getRaza() * c.getRaza() * Math.PI);};
        Area<Patrat> patratArea = (p) ->{return (float)(p.getLatura()*p.getLatura());};
        Cerc cerc=new Cerc(5);
        Patrat patrat=new Patrat(4);
        System.out.println("Cu functii lambda: ");
        System.out.println(cercArea.compute(cerc));
        System.out.println(patratArea.compute(patrat));

        //Metoda cu helper
        System.out.println("Cu helper: ");
        Area<Cerc> cercArea1 = AreaHelper::computeCircleArea;
        System.out.println(cercArea1.compute(cerc));

        //Metoda 3 - asa nu
        Area<Cerc> cercArea2=new Area<Cerc>() {
            @Override
            public float compute(Cerc entity) {
                return (float)(entity.getRaza() * entity.getRaza() * Math.PI);
            }
        };

        //Lista de cercuri
        List<Cerc> list= Arrays.asList(new Cerc(2),new Cerc(3),new Cerc(4));

        System.out.println("Lista de cercuri: ");
        printArie(list,cercArea);

        System.out.println("Lista de cercuri (lambda direct): ");
        printArie(list,x->{return (float)(x.getRaza()*x.getRaza()*Math.PI);});

        System.out.println("Lista de cercuri (helper): ");
        printArie(list,AreaHelper::computeCircleArea);
    }
}
