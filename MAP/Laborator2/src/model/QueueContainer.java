package model;

import java.util.ArrayList;

//ex 5.2
public class QueueContainer extends SuperContainer {

    public QueueContainer() {
        super();
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
