package sem1_2;

import sem1_2.decorator.PrinterTaskRunner;
import sem1_2.decorator.StrategyTaskRunner;
import sem1_2.decorator.TaskRunner;
import sem1_2.factory.Strategy;
import sem1_2.model.Container;
import sem1_2.model.MessageTask;
import sem1_2.model.StackContainer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Sem1_2Test {
    public static MessageTask[] createMessages() {
        MessageTask msg1 = new MessageTask("1", "feedback lab 2", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg2 = new MessageTask("2", "feedback lab 3", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg3 = new MessageTask("3", "feedback lab 4", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg4 = new MessageTask("4", "feedback lab 5", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg5 = new MessageTask("5", "feedback lab 6", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());

        MessageTask[] messages = new MessageTask[]{msg1, msg2, msg3, msg4, msg5};

        return messages;
    }

    /**
     * @return
     */
    public List<MessageTask> createMessages2() {
        MessageTask msg1 = new MessageTask("1", "feedback lab 2", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg2 = new MessageTask("2", "feedback lab 3", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg3 = new MessageTask("3", "feedback lab 4", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg4 = new MessageTask("4", "feedback lab 5", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        MessageTask msg5 = new MessageTask("5", "feedback lab 6", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());

        MessageTask[] messages = new MessageTask[]{msg1, msg2, msg3, msg4, msg5};

        return Arrays.asList(messages);
//        List<MessageTask> messageTasks = new ArrayList<>();
//        messageTasks.add(msg1);
    }

    public static void executeTasks(TaskRunner t) {
        t.executeAll();
    }


    public static void main(String[] args) {
        MessageTask[] messageTasks = createMessages();
        /*for (MessageTask messageTask: messageTasks
             ) {
            System.out.println(messageTask);
        }*/

        Container stack = new StackContainer();
        stack.add(messageTasks[0]);

        TaskRunner t = new StrategyTaskRunner(Strategy.valueOf(args[0]));

        for (MessageTask m : messageTasks) {
            t.addTask(m);
        }

        //executeTasks(t);
        TaskRunner decorator = new PrinterTaskRunner(t);
        decorator.executeAll();
    }
}
