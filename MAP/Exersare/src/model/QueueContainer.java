package model;

import java.util.ArrayList;

public class QueueContainer extends SuperContainer {

    public QueueContainer(ArrayList<Task> queue) {
        super(queue);
    }

    @Override
    public Task remove() {
        if(this.c.isEmpty())
            return null;
        else {
            Task t=this.c.remove(0);
            return t;
        }
    }
}
