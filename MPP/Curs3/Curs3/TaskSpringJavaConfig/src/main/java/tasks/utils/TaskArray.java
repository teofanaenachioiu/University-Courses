package tasks.utils;

import tasks.model.Task;

import java.util.Iterator;

/**
 * Created by grigo on 9/27/16.
 */
public class TaskArray implements Iterable<Task>{
    private Task[] tasks;
    private int cap=10;
    private int size;

    public TaskArray() {
        tasks=new Task[cap];
        size=0;
    }

    public TaskArray(int cap) {
        this.cap = cap;
        tasks=new Task[cap];
        size=0;
    }

    public void add(Task t){
        if (size==cap)
            resize();
        tasks[size++]=t;
    }

    private void resize() {
        cap*=2;
        Task[] tmp=new Task[cap];
        for(int i=0;i<size;i++)
            tmp[i]=tasks[i];
        tasks=tmp;
    }

    public int getSize() {
        return size;
    }

    public Task get(int pos){
        if ((pos>=0) && (pos<size))
            return tasks[pos];
        return null;
    }

    public void add(Task t, int pos){
        if (size==cap)
            resize();
        for (int i=size;i>pos;i++){
            tasks[i]=tasks[i-1];
        }
        tasks[pos]=t;
        size++;
    }

    public Task delete(int pos){
        if ((pos>=0)&& (pos<size)){
            Task res=tasks[pos];
            for(int i=pos; i<size-1;i++){
                tasks[i]=tasks[i+1];
            }
            size--;
            return res;
        }
        return null;
    }

    public Iterable<Task> getAll(){
        return this;
    }
    @Override
    public Iterator<Task> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements  Iterator<Task>{
        private int cursor=0;
        @Override
        public boolean hasNext() {
            return cursor<size;
        }

        @Override
        public Task next() {
            return tasks[cursor++];
        }
    }
}
