package common.utils.taskChangeEvent;

import common.domain.MessageTask;
import common.utils.taskChangeEvent.ChangeEventType;
import common.utils.taskChangeEvent.Event;

public class MessageTaskChangeEvent implements Event {
    private ChangeEventType type;
    private MessageTask data, oldData;

    public MessageTaskChangeEvent(ChangeEventType type, MessageTask data) {
        this.type = type;
        this.data = data;
    }
    public MessageTaskChangeEvent(ChangeEventType type, MessageTask data, MessageTask oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public MessageTask getData() {
        return data;
    }

    public MessageTask getOldData() {
        return oldData;
    }
}