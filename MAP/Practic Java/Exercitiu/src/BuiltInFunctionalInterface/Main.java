package BuiltInFunctionalInterface;

import java.time.LocalDate;
import java.util.function.*;
import java.util.stream.StreamSupport;

class StringHelper{
    public static boolean incepeCuLiteraA(String str){
        return str.charAt(0)=='a';
    }

    public static boolean nuSeTerminaCuA(String str){
        return str.charAt(str.length()-1)!='a';
    }
}

public class Main {
    public static void main(String[] args) {

        //CONSUMER: accept(T t) -> performs this operation on the given argument

        //method reference
        Consumer<String> consumer1=System.out::println;
        consumer1.accept("refernita la metoda");
        //lambda function
        Consumer<String> consumer2=x->System.out.println(x);
        consumer2.accept("lambda");

        //PREDICATE:
        //test(T t) -> evaluate this predicate on the given argument

        //lambda function
        Predicate<String> predicate1=x->x.charAt(0)=='a';
        System.out.println(predicate1.test("abracadabra"));
        System.out.println(predicate1.test("ceva"));

        //method reference
        Predicate<String> predicate2=StringHelper::incepeCuLiteraA;
        System.out.println(predicate2.test("anaa"));

        //and(Predicate<? super T> other) -> returns a composite predicate that represents a short
        //                                   circulating logical AND of this predicate and another
        Predicate<String> predicate3=StringHelper::nuSeTerminaCuA;
        Predicate<String> predicate4=predicate2.and(predicate3);
        System.out.println(predicate4.test("anaana"));

        //FUNCTION

        Function<String,Integer> toIntegerLambda=x->Integer.valueOf(x);
        Function<String,Integer> toIntegerRef=Integer::valueOf;

        System.out.println(toIntegerLambda.apply("123"));
        System.out.println(toIntegerRef.apply("159"));

        Function<Integer,String> toStringLambda=x->String.valueOf(x);
        Function<Integer,String> toStringRef= String::valueOf;

        System.out.println(toStringLambda.apply(10));
        System.out.println(toStringRef.apply(11));

        Function<String,String> backToString=toIntegerLambda.andThen(toStringLambda);
        System.out.println(backToString.apply("100"));

        //BIFUNCTION
        BiFunction<String, Integer,String> concat=(x,y)->x.concat(y.toString());
        System.out.println(concat.apply("ana",12));

        //UNARYOPERATOR
        UnaryOperator<String> uno=x->x.toUpperCase();
        System.out.println(uno.apply("ana"));

        //BINARYOPERATOR
        BinaryOperator<String> con=String::concat;
        System.out.println(con.apply("ana","maria"));

        //SUPPLIER

        //Rerefinta la metoda
        Supplier<LocalDate> supplier=()->LocalDate.now();
        //Functie Lambda
        Supplier<LocalDate> supplier1=LocalDate::now;

        System.out.println(supplier.get());
        System.out.println(supplier1.get());
    }
}
