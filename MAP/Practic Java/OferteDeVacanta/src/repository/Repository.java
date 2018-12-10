package repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<ID, E> {
    Collection<E> findAll();
}