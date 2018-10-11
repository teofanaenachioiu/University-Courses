package model;

import java.util.ArrayList;

public abstract class SuperContainer implements Container {
    ArrayList<Task> c;

    public SuperContainer(ArrayList<Task> c) {
        this.c = new ArrayList<>();
    }

    @Override
    public abstract Task remove();

    @Override
    public void add(Task task) {
        this.c.add(task);
    }

    @Override
    public int size() {
            return this.c.size();
    }

    @Override
    public boolean isEmpty() {
        return this.c.isEmpty();
    }
}
