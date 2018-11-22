package common.service;

import common.domain.MessageTask;
import common.repository.CrudRepository;
import common.utils.taskChangeEvent.ChangeEventType;
import common.utils.taskChangeEvent.MessageTaskChangeEvent;
import common.utils.observer.Observable;
import common.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MessageTaskService implements Observable<MessageTaskChangeEvent> {
    private CrudRepository<String, MessageTask> repo;

    public MessageTaskService(CrudRepository<String, MessageTask> repo) {
        this.repo = repo;
    }

    private <T> Iterable <T> filter(Iterable <T> list, Predicate<T> cond)
    {
        List<T> rez=new ArrayList<>();
        list.forEach((T x)->{if (cond.test(x)) rez.add(x);});
        return rez;
    }

    public Iterable<MessageTask> bySubject(String subject) {
        return filter(repo.findAll(), messageTask -> messageTask.getDescription().contains(subject));
    }



    public MessageTask addMessageTaskTask(MessageTask messageTask) {
        MessageTask task = repo.save(messageTask);
        if(task == null) {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.ADD,task));
        }
        return task;
    }

    public MessageTask deleteMessageTask(MessageTask t){
        MessageTask task=repo.delete(t.getID());
        if(task!=null) {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.DELETE, task));
        }
        return task;
    }

    public MessageTask updateMessageTask(MessageTask newTask) {
        MessageTask oldTask=repo.findOne(newTask.getID());
        if(oldTask!=null) {
            MessageTask res=repo.update(newTask);
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.UPDATE, newTask, oldTask));
            return res;
        }
        return oldTask;
    }

    public Iterable<MessageTask> getAll(){
        return repo.findAll();
    }

    private List<Observer<MessageTaskChangeEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<MessageTaskChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<MessageTaskChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(MessageTaskChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

}

