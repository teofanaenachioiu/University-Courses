package utils;

public class DataChanged implements Event {
    private EventType type;

    public DataChanged(EventType type) {
        this.type = type;
    }
}