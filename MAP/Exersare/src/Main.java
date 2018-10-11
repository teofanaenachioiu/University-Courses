import model.MessageTask;
import model.SortingTask;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
	    MessageTask message=new MessageTask("1","Feedback de la profa","Esti bun","profa","student", LocalDateTime.now());
        message.execute();
        Integer[] arr={1,7,5,9,0};
        SortingTask sortt=new SortingTask("2","Sortare intregi",arr);
        sortt.execute();
    }
}
