package MetodePeMAP;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Metode {
    public static void main(String[] args) {
        BiFunction<String,String,String> mapper=(v,w)->v.concat(" ").concat(w);

        Map<String,String> students=new HashMap<>();
        students.put("Popescu","Dan");
        students.put("Vasilescu","Ilie");

        students.computeIfPresent("Popescu",mapper);
        students.computeIfPresent("Vasilescu",mapper);

        students.forEach((x,y)->System.out.println(x+"="+y));
    }
}
