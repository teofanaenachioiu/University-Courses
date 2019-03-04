package tasks.utils;

import tasks.model.SortingTask;

/**
 * Created by grigo on 11/16/16.
 */
public class SortingTaskEvent implements Event {
    private STEType type;
    private SortingTask data, oldData;

    public SortingTaskEvent(STEType type, SortingTask data) {
        this.type = type;
        this.data = data;
    }
    public SortingTaskEvent(STEType type, SortingTask data, SortingTask oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public STEType getType() {
        return type;
    }

    public SortingTask getData() {
        return data;
    }

    public SortingTask getOldData() {
        return oldData;
    }
}
