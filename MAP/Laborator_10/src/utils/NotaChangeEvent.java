package utils;

import domain.Nota;

public class NotaChangeEvent implements Event{
    private ChangeEventType type;
    private Nota data, oldData;

    public NotaChangeEvent(ChangeEventType type) {
        this.type = type;
    }

    public NotaChangeEvent(ChangeEventType type, Nota data) {
        this.type = type;
        this.data = data;
    }
    public NotaChangeEvent(ChangeEventType type, Nota data, Nota oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Nota getData() {
        return data;
    }

    public Nota getOldData() {
        return oldData;
    }
}