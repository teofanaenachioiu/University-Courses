package common.utils.observer;

import common.utils.taskChangeEvent.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers( E t);
}
