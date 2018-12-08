package ExercitiuCurs5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) {
        // Ex 1
        // Sa se stearga dintr-o lista de siruri de caractere toate elementele care incep cu "a"

        List<String> listaStr=new ArrayList<>();
        listaStr.add("ana");
        listaStr.add("maria");
        listaStr.add("alina");
        listaStr.add("larian");

        Predicate<String> predicate=x->x.charAt(0)!='a';
        listaStr = listaStr.stream().filter(predicate).collect(Collectors.toList());
        listaStr.forEach(System.out::println);

        // Ex 2
        // Sa se stearga dintr-o lista de siruri de caractere toate elementele care sunt prefixele
        // unui cuvant. De exemplu "Anamaria". Folositi functii lambda si referinta la metoda.

        List<String> listaNume=new ArrayList<>();
        listaNume.add("Anamaria");
        listaNume.add("Andreea");
        listaNume.add("Anania");
        listaNume.add("Anisia");

        String prefix="Ana";
        Predicate<String> predicate1=x->!x.startsWith(prefix);
        listaNume = listaNume.stream().filter(predicate1).collect(Collectors.toList());
        listaNume.forEach(System.out::println);

        //Exercitii comparatori

        List<Student> listaStd=new ArrayList<>();
        listaStd.add(new Student("1","Popa","Maria"));
        listaStd.add(new Student("2","Apopei", "Cristina"));
        listaStd.add(new Student("3","Morar","Silviu"));

        //Sortare folosind clasa StudentHelper

        System.out.println("Sortare folosind clasa StudentHelper:");
        Comparator<Student> comparator=StudentHelper::compareByName;
        listaStd.sort(comparator);
        listaStd.forEach(System.out::println);

        //Sortare folosind functii lambda

        System.out.println("Sortare folosind functii lambda:");
        listaStd.sort((x,y)->x.getNume().compareTo(y.getNume()));
        listaStd.forEach(System.out::println);

    }
}
