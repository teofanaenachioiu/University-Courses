package tasks.repository;

/**
 * Created by grigo on 11/14/16.
 */
public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}
