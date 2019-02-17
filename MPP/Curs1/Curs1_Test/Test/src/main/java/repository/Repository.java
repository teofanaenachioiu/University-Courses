package repository;

import domain.ValidationException;

public interface Repository<ID, E> {
    long size();
    E save(E entity) throws ValidationException;
    E update(E entity) throws ValidationException;
    E delete(ID id);
    E findOne(ID id);
    Iterable<E> findAll();
}

