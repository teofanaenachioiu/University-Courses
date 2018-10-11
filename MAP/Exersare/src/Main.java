import model.MessageTask;
import model.SortingTask;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void test(){
        ArrayList<MessageTask> arr=new ArrayList<>();
        arr.add(new MessageTask("1","Feedback MAP","Ai nota 10","Prof","student",LocalDateTime.now()));
        arr.add(new MessageTask("2","Feedback BD","Ai nota 7","Prof","student",LocalDateTime.now()));
        arr.add(new MessageTask("3","Feedback PS","Ai nota 8","Prof","student",LocalDateTime.now()));
        arr.add(new MessageTask("4","Feedback PLF","Ai nota 9","Prof","student",LocalDateTime.now()));
        arr.add(new MessageTask("5","Feedback RC","Ai nota 10","Prof","student",LocalDateTime.now()));
        for (MessageTask t:arr) {
            System.out.println(t.toString());
        }
    }

    public static ArrayList<Integer> creareVector(){
        ArrayList<Integer> arr=new ArrayList<Integer>();
        arr.add(5);
        arr.add(1);
        arr.add(15);
        arr.add(3);
        arr.add(9);
        arr.add(0);
        return arr;
    }

    public static void main(String[] args) {
        //ex 3
        ArrayList<Integer> arr=creareVector();
        SortingTask sorter=new SortingTask("2","Sortare intregi",arr);
        sorter.execute();

        //ex 4
        test();
    }
}
