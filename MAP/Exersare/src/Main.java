import model.MessageTask;
import model.SortingTask;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

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
	    MessageTask message=new MessageTask("1","Feedback de la profa","Esti bun","profa","student", LocalDateTime.now());
        message.execute();
        ArrayList<Integer> arr=creareVector();
        SortingTask sorter=new SortingTask("2","Sortare intregi",arr);
        sorter.execute();
    }
}
