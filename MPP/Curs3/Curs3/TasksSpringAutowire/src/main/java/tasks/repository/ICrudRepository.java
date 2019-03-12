package tasks.repository;

import tasks.utils.HasId;

/**
 * Created by grigo on 11/14/16.
 */
public interface ICrudRepository<ID, T extends HasId<ID>> {
    int size();
    void save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}