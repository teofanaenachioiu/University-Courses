package common.utils.observer;

import common.utils.taskChangeEvent.Event;

public interface Observer<E extends Event> {
    void update(E e);
}