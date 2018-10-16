import model.Container;
import model.MessageTask;
import model.SortingTask;
import model.Strategy;
import runner.DelayTaskRunner;
import runner.PrinterTaskRunner;
import runner.StrategyTaskRunner;
import runner.TaskRunner;

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

    public static void addTaskInRunner(TaskRunner runner){
        runner.addTask(new MessageTask("1","Feedback MAP","Ai nota 10","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("2","Feedback BD","Ai nota 7","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("3","Feedback PS","Ai nota 8","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("4","Feedback PLF","Ai nota 9","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("5","Feedback RC","Ai nota 10","Prof","student",LocalDateTime.now()));
    }

    public static void testeRunner1(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
    }

    public static void testeRunner2(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
        TaskRunner decorator=new PrinterTaskRunner(runner);
        addTaskInRunner(decorator);
        decorator.executeAll();
    }

    public static void testeRunner3(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
        TaskRunner decorator=new PrinterTaskRunner(runner);
        addTaskInRunner(decorator);
        decorator.executeAll();
        TaskRunner deco =new DelayTaskRunner(decorator);
        addTaskInRunner(deco);
        deco.executeAll();
    }

    public static void main(String[] args) {
        //ex 3
        /*System.out.println("TEST EX 3");
        ArrayList<Integer> arr=creareVector();
        SortingTask sorter=new SortingTask("2","Sortare intregi",arr);
        sorter.execute();*/

        //ex 4
        /*System.out.println("TEST EX 4");
        test();*/

        //ex 10
        /*System.out.println("TEST EX 4");
        testeRunner1(Strategy.valueOf(args[0]));*/

        //ex 13
        /*System.out.println("TEST EX 13");
        testeRunner2(Strategy.valueOf(args[0]));*/

        //ex 14
        System.out.println("TEST EX 14");
        testeRunner3(Strategy.valueOf(args[0]));
    }
}
