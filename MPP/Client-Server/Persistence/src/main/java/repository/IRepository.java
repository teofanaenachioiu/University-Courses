package repository;

import javafx.util.Pair;

public interface IRepository<ID, T> {
    int size();
    ID save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}