package tasks.utils;

/**
 * Created by grigo on 11/16/16.
 */
public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
