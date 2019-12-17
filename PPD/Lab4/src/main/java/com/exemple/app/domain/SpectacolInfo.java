package com.exemple.app.domain;

import java.util.ArrayList;
import java.util.List;

public class SpectacolInfo {
    public static int pret1 = 10;
    public static int pret2 = 20;
    public static int pret3 = 30;
    public static List<Integer> listaLocuriCatg1 = new ArrayList<>();
    public static List<Integer> listaLocuriCatg2 = new ArrayList<>();
    public static List<Integer> listaLocuriCatg3 = new ArrayList<>();

    static {
        for (int i = 1; i <= 100; i++) {
            listaLocuriCatg1.add(i);
        }
        for (int i = 100 + 1; i <= 200; i++) {
            listaLocuriCatg2.add(i);
        }
        for (int i = 200 + 1; i <= 300; i++) {
            listaLocuriCatg3.add(i);
        }
    }

    public static Loc getLoc(int numarScaun) {
        if (listaLocuriCatg1.contains(numarScaun))
            return Loc.builder()
                    .categorie(Categorie.CATG1)
                    .numar(numarScaun)
                    .pret(pret1)
                    .build();
        if (listaLocuriCatg2.contains(numarScaun))
            return Loc.builder()
                    .categorie(Categorie.CATG2)
                    .numar(numarScaun)
                    .pret(pret2)
                    .build();
        if (listaLocuriCatg3.contains(numarScaun))
            return Loc.builder()
                    .categorie(Categorie.CATG3)
                    .numar(numarScaun)
                    .pret(pret3)
                    .build();
        return null;
    }
}
