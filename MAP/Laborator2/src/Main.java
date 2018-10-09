import model.Container;
import model.MessageTask;
import model.StackContainer;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        MessageTask[] messages = new MessageTask[2];

        MessageTask messageTask = new MessageTask("1",
                "Feedback la MAP",
                "Esti foarte bun",
                "Profa",
                "Student",
                LocalDateTime.now());
        // System.out.println(messageTask.toString());
        //messageTask.execute();
        // messages[0] = messageTask;
        // System.out.println(messages.length);
        Container c=new StackContainer();
        c.add(messageTask);
        System.out.println("Hello World!");

    }
}
