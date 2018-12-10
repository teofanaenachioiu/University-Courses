package repository;

import domain.HasID;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RepositoryInMemory <ID, E extends HasID<ID>> implements Repository<ID,E>{
    protected Map<ID,E> entities;

    public RepositoryInMemory() {
        entities=new HashMap<>();
    }

    public Optional<E> findOne(ID id) {
        if (id == null) throw new IllegalArgumentException("Nu ai dat parametru");
        return Optional.ofNullable(entities.get(id));

    }

    @Override
    public Collection<E> findAll() {
        return entities.values();
    }

    public Optional<E> save(E entity) {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        return Optional.ofNullable(entities.put(entity.getID(), entity));
    }
}
