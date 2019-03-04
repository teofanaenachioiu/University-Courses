package tasks.service;

import tasks.model.SortingTask;
import tasks.repository.IRepository;
import tasks.repository.SortingTaskRepository;
import tasks.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grigo on 11/16/16.
 */
public class TaskService implements Observable<SortingTaskEvent> {
    private IRepository<Integer, SortingTask> repo;
    private ObservableTaskRunner runner;
    public TaskService(IRepository<Integer, SortingTask> repo){

        this.repo=repo;
    }
    public TaskService(IRepository<Integer, SortingTask> repo, ObservableTaskRunner runner){

        this.repo=repo;
        this.runner=runner;
    }

    public void addSortingTask(SortingTask task){

        repo.save(task);
        notifyObservers(new SortingTaskEvent(STEType.ADD,task));
    }

    public void deleteSortingTask(SortingTask task){
        repo.delete(task.getId());
        notifyObservers(new SortingTaskEvent(STEType.DELETE, task));
    }
    public Iterable<SortingTask> getAll(){
        return repo.findAll();
    }

    private List<Observer<SortingTaskEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<SortingTaskEvent> e) {
        observers.add(e);

    }

    public void addRunnerObserver(Observer<TaskEvent> obs){
        runner.addObserver(obs);
    }
    public void removeRunnerObserver(Observer<TaskEvent> obs){
        runner.removeObserver(obs);
    }
    @Override
    public void removeObserver(Observer<SortingTaskEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(SortingTaskEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public void updateSortingTask(SortingTask oldTask, SortingTask newTask) {
        repo.update(oldTask.getId(),newTask);
        notifyObservers(new SortingTaskEvent(STEType.UPDATE,newTask,oldTask));
    }

    public void addTaskToRunner(int taskId){
        SortingTask task=repo.findOne(taskId);
        runner.addTask(task);
    }
    public void executeOneTask(){
        runner.executeOneTask();
    }

    public void executeAll(){
        runner.executeAll();
    }

    public void close(){
        runner.close();
    }

    public void cancelRunner(){
        runner.cancel();
    }
}
