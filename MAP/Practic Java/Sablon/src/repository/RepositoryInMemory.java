package repository;

import domain.HasID;
import validation.Validator;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RepositoryInMemory <ID, E extends HasID<ID>> implements CrudRepository<ID,E>{
    private Validator<E> validator;
    protected Map<ID,E> entities;

    public RepositoryInMemory(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null) throw new IllegalArgumentException("Nu ai dat parametru");
        return Optional.ofNullable(entities.get(id));

    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) throws ValidationException {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        validator.validate(entity);
        return Optional.ofNullable(entities.put(entity.getID(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
       // Optional<E> temp = Optional.ofNullable(entities.get(id));
        return Optional.of(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        validator.validate(entity);
        Optional<E> tmp=Optional.ofNullable(entities.get(entity.getID()));
        if(tmp.isPresent()) {
            entities.put(entity.getID(),entity);
            return Optional.empty();
        }
        else
            return Optional.of(entity);
    }
}
