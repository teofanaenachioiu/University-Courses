package sem1_2.model;

import java.util.ArrayList;
import java.util.List;

public class StackContainer implements Container {
    List<Task> stack;

    public StackContainer() {
        this.stack = new ArrayList<>();
    }

    @Override
    public Task remove() {
        if(stack.isEmpty())
            return null;
        Task r = this.stack.remove(stack.size()-1);
        return r;
    }

    @Override
    public void add(Task task) {
        stack.add(task);
    }

    @Override
    public int size() {

        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
