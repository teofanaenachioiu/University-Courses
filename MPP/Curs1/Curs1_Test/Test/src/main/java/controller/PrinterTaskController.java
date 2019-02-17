package controller;

import domain.MessageTask;
import utils.ListEvent;
import utils.Observable;
import utils.Observer;

/**
 * Created by camelia on 11/5/2017.
 */
public class PrinterTaskController implements Observer<MessageTask> {
    @Override
    public void notifyEvent(ListEvent<MessageTask> e) {
        switch (e.getType())
        {
            case ADD:
                System.out.println("A fost adaugat un MessageTask");
                break;
            case REMOVE:
                System.out.println("A fost sters un MessageTask");
                break;
            case UPDATE:
                System.out.println("A fost modificat un MessageTask");
                break;
        }
        e.getList().forEach(System.out::println);
    }
}
