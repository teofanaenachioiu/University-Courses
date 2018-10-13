package model;

import java.util.ArrayList;
import java.util.List;

public class StackContainer implements Container {
    List<Task> stack;

    public StackContainer() {
        this.stack = new ArrayList<>();
    }

    @Override
    public Task remove() {
        return null;
    }

    @Override
    public void add(Task task) {
        stack.add(task);
        System.out.println("");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
