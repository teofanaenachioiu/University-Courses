package Stream;

import java.util.Arrays;
import java.util.Optional;

import static java.lang.Class.forName;

public class Main {
    public static void main(String[] args) {
        Integer[] lista={1,2,9,4,6,9,11,7};
        Optional<Integer> rez;
        rez= Arrays.stream(lista).reduce(
                (x,y)->x>y?x:y
        );
        rez.ifPresent(System.out::println);

        //Reflection
        try {
            Class myClass=Class.forName("Stream.Main");
            System.out.println(myClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
