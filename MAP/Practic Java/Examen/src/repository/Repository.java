package repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Repository<ID, E> {

//    E findOne(ID id);

    Collection<E> findAll();

//    E save(E entity);
//
//    E delete(ID id);
//
//    E update(E entity);

//    Optional<E> findOne(ID id);
//
//    Iterable<E> findAll();
//
//    Optional<E> save(E entity);
//
//    Optional<E> delete(ID id);
//
//    Optional<E> update(E entity);

}
