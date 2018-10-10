import decorator.PrinterTaskRunner;
import decorator.StrategyTaskRunner;
import decorator.TaskRunner;
import model.Container;
import model.MessageTask;
import model.StackContainer;
import model.Strategy;

import java.time.LocalDateTime;

public class Main {

    public static MessageTask [] createMessages(){
        MessageTask messageTask1 = new MessageTask("1",
                "Feedback la MAP",
                "Esti foarte bun",
                "Profa",
                "Student",
                LocalDateTime.now());
        MessageTask messageTask2 = new MessageTask("2",
                "Feedback la MAP",
                "Esti bun",
                "Profa",
                "Student",
                LocalDateTime.now());
        MessageTask messageTask3 = new MessageTask("3",
                "Feedback la MAP",
                "Ma abtin",
                "Profa",
                "Student",
                LocalDateTime.now());
        //MessageTask [] rez=new MessageTask[]{messageTask1,messageTask2,messageTask3};
        MessageTask [] rez={messageTask1,messageTask2,messageTask3};
        return rez;
    }

    public static void main(String[] args) {
        MessageTask[] messages =createMessages();

        TaskRunner runner=new StrategyTaskRunner(Strategy.valueOf(args[0]));

        for (MessageTask t:messages) {
            runner.addTask(t);
        }
        //runner.executeAll();

        TaskRunner decorator=new PrinterTaskRunner(runner);
        decorator.executeAll();

//        MessageTask messageTask = new MessageTask("1",
//                "Feedback la MAP",
//                "Esti foarte bun",
//                "Profa",
//                "Student",
//                LocalDateTime.now());
        // System.out.println(messageTask.toString());
        //messageTask.execute();
        // messages[0] = messageTask;
        // System.out.println(messages.length);
        /*Container c=new StackContainer();
        c.add(messageTask);
        System.out.println("Hello World!");*/


    }
}
