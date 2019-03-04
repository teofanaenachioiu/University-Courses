package tasks.utils;



import tasks.model.Task;
import tasks.model.TaskExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by grigo on 11/8/16.
 */
public class ObservableTaskRunner  implements ITaskRunner, Observable<TaskEvent>{
    protected Container container;

    public ObservableTaskRunner(Container container) {
        this.container = container;
    }

    protected List<Observer<TaskEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<TaskEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<TaskEvent> e) {
        observers.remove(e);
    }

    @Override
    public  void notifyObservers(TaskEvent t) {
        for(Observer<TaskEvent> ovs:observers)
            ovs.update(t);

    }


    @Override
    public void executeOneTask() {
        Task task=container.remove();
        notifyObservers(new TaskEvent(TaskEventType.StartingTaskExecution, task));
        task.execute();
        notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCompleted,task));
    }

    ExecutorService executor;
    @Override
    public void executeAll() {
        //while(!container.isEmpty())
        executor= Executors.newFixedThreadPool(container.size());
        List<Callable<Void>> tasks=new ArrayList<>();

        for(Task t:container.getAll()){
           Callable<Void> callable=()->{
               try{
                   System.out.println("notificat observer a "+t.getTaskID());
                   notifyObservers(new TaskEvent(TaskEventType.StartingTaskExecution,t));
                  // System.out.println("notificat observer b "+t.getTaskID());
                   t.execute();
                  // System.out.println("notificat observer c"+t.getTaskID());
                   notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCompleted,t));
                   System.out.println("notificat observer d"+t.getTaskID());
               }catch(TaskExecutionException te){
                    notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCancelled,t));
               }
               return null;
           };
            tasks.add(callable);
        }
        try {
            executor.invokeAll(tasks).stream().map(future->{try{
                future.get();
            }catch (Exception e){
                System.out.println("eroare "+e);
            }
                return null;
            });
        }catch(InterruptedException ex){
            System.out.println("executeAll interrupted ...");
        }

        executor.shutdown();
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    public void cancel(){
        if ((executor!=null)&& (!executor.isTerminated())){
            executor.shutdown();
        }
    }
    public void close(){
        if ((executor!=null)&&(!executor.isTerminated())){
            System.out.println("Shutting down executor ...");
            executor.shutdownNow();
        }
    }
}
