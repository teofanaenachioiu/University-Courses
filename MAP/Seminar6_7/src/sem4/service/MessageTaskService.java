package sem4.service;

import sem4.domain.MessageTask;
import sem4.repository.CrudRepository;
import sem4.utils.ChangeEventType;
import sem4.utils.MessageTaskChangeEvent;
import sem4.utils.Observable;
import sem4.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public MessageTask deleteSortingTask(MessageTask t){
        MessageTask task=repo.delete(t.getID());
        if(task!=null) {
            notifyObservers(new MessageTaskChangeEvent(ChangeEventType.DELETE, task));
        }
        return task;
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

    public void updateSortingTask(MessageTask oldTask, MessageTask newTask) {
        repo.update(newTask);
        notifyObservers(new MessageTaskChangeEvent(ChangeEventType.UPDATE,newTask,oldTask));
    }

}
