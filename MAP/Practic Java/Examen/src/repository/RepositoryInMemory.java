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

//    @Override
//    public Optional<E> findOne(ID id) {
//        if (id == null) throw new IllegalArgumentException("Nu ai dat parametru");
//        return Optional.ofNullable(entities.get(id));
//
//    }
//
//    @Override
//    public Iterable<E> findAll() {
//        return entities.values();
//    }
//
//    @Override
//    public Optional<E> save(E entity)  {
//        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
//        return Optional.ofNullable(entities.put(entity.getID(), entity));
//    }
//
//    @Override
//    public Optional<E> delete(ID id) {
//        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
//        return Optional.of(entities.remove(id));
//    }
//
//    @Override
//    public Optional<E> update(E entity) {
//        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
//        Optional<E> tmp=Optional.ofNullable(entities.get(entity.getID()));
//        if(tmp.isPresent()) {
//            entities.put(entity.getID(),entity);
//            return Optional.empty();
//        }
//        else
//            return Optional.of(entity);
//    }


    public E findOne(ID id) {
        if (id == null) throw new IllegalArgumentException("Nu ai dat parametru");
        return entities.get(id);
    }

    @Override
    public Collection<E> findAll() {
        return entities.values();
    }

    public E save(E entity)  {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        return entities.put(entity.getID(), entity);
    }


    public E delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
        return entities.remove(id);
    }

    public E update(E entity) {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        Optional<E> tmp=Optional.ofNullable(entities.get(entity.getID()));
        if(tmp.isPresent()) {
            entities.put(entity.getID(),entity);
            return null;
        }
        else
            return entity;
    }
}