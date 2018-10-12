package model;

import java.util.ArrayList;

public abstract class SuperContainer implements Container{
    protected ArrayList<Task> c;

    public SuperContainer() {
        this.c = new ArrayList<>();
    }

    public abstract Task remove();

    public void add(Task task) {
        this.c.add(task);
    }

    public int size() {
            return this.c.size();
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }
}
