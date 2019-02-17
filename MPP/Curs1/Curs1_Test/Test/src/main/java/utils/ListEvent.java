package utils;

import domain.HasID;

import java.util.Iterator;

public abstract class ListEvent<E> {

    private ListEventType type;
    public ListEvent(ListEventType type){
        this.type = type;
    }

    //set get
    public ListEventType getType() {
        return type;
    }
    public void setType(ListEventType type) {
        this.type = type;
    }
    /**
     * returneaza lista implicata in eveniment
     * @return
     */
    public abstract Iterable<E> getList();

    /**
     * returneaza elementul implicat in eveniment
     * @return
     */
    public abstract E getElement();

}


