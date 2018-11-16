package sem1_2.model;

import java.util.ArrayList;

public class StackContainer implements Container {
    ArrayList<Task> tasks = new ArrayList<>();


    @Override
    public Task remove()
    {
        if (!tasks.isEmpty())
            return tasks.remove(tasks.size()-1);
        else
            return null;
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
        //System.out.println("");
    }

    @Override
    public int size() {
        return tasks.size();

    }

    @Override
    public boolean isEmpty() {
        if (tasks.size()!=0)
            return false;
        else
            return true;

    }
}
