package tasks.utils;

/**
 * Created by grigo on 11/16/16.
 */
public interface Observer<E extends Event> {
    void update(E e);

}
