package service;

import domain.MessageTask;
import domain.ValidationException;
import repository.Repository;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TaskServiceManager implements Observable<MessageTask>{
    private Repository<String, MessageTask> taskRepo;
    ArrayList<Observer<MessageTask>> messageTaskObservers=new ArrayList<>();

    public TaskServiceManager(Repository<String, MessageTask> repo) {
        taskRepo = repo;
    }

    public MessageTask saveMessageTask(MessageTask t) throws ValidationException {
        MessageTask r=taskRepo.save(t);
        if (r==null) {
            ListEvent<MessageTask> ev = createEvent(ListEventType.ADD, r, taskRepo.findAll());
            notifyObservers(ev);
        }
        return r;
    }

    public MessageTask deleteMessageTask(String taskId) {
        MessageTask r=taskRepo.delete(taskId);
        if (r!=null) {
            ListEvent<MessageTask> ev = createEvent(ListEventType.REMOVE, r, taskRepo.findAll());
            notifyObservers(ev);
        }
        return r;
    }

    public MessageTask updateMessageTask(MessageTask t) throws ValidationException {
        MessageTask r=taskRepo.update(t);
        if (r!=null) {
            ListEvent<MessageTask> ev = createEvent(ListEventType.REMOVE, r, taskRepo.findAll());
            notifyObservers(ev);
        }
        return r;
    }

    public List<MessageTask> getAllMessageTasks()
    {
        return StreamSupport.stream(taskRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    @Override
    public void addObserver(Observer<MessageTask> o) {
        messageTaskObservers.add(o);

    }

    @Override
    public void removeObserver(Observer<MessageTask> o) {
        messageTaskObservers.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<MessageTask> event) {
        messageTaskObservers.forEach(x->x.notifyEvent(event));
    }

    private <E> ListEvent<E> createEvent(ListEventType type, final E elem, final Iterable<E> l){
        return new ListEvent<E>(type) {
            @Override
            public Iterable<E> getList() {
                return l;
            }
            @Override
            public E getElement() {
                return elem;
            }
        };
    }
}
