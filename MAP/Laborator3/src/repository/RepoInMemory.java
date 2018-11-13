package repository;

import domain.HasID;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import repository.CrudRepository;
import validator.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * CRUD operations repository
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of entities saved in repository
 */
public class RepoInMemory<ID, E extends HasID<ID>> implements CrudRepository<ID, E>{
    private Validator<E> validator;
    protected Map<ID,E> entities;

    /**
     * Constructorul clasei
     * @param validator - entitate Validator (validarea datelor)
     */
    public RepoInMemory(Validator<E> validator){
        this.validator=validator;
        entities=new HashMap<ID,E>();
    }
    /**
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    @Override
    public Optional<E> findOne(Optional<ID> id) {
        id.orElseThrow(()-> new IllegalArgumentException("Nu ai dat parametru"));
        return Optional.ofNullable(entities.get(id.get()));
    }
    /**
     *
     * @return all entities
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    @Override
    public Optional<E> save(Optional<E> entity) throws ValidationException {
        entity.orElseThrow(()-> new IllegalArgumentException("Nu ai dat parametru"));
        validator.validate(entity.get());
        return Optional.ofNullable(entities.put(entity.get().getID(), entity.get()));

    }
    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    @Override
    public Optional<E> delete(Optional<ID> id) {
        id.orElseThrow(()-> new IllegalArgumentException("Nu ai dat parametru"));
        Optional<E> temp = Optional.ofNullable(entities.get(id.get()));
        return Optional.ofNullable(entities.remove(id.get()));
    }
    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     */
    @Override
    public Optional<E> update(Optional<E> entity) {
        entity.orElseThrow(()-> new IllegalArgumentException("Nu ai dat parametru"));
        validator.validate(entity.get());
        Optional<E> tmp=Optional.ofNullable(entities.get(entity.get().getID()));
        if(tmp.isPresent()) {
            entities.put(entity.get().getID(),entity.get());
            return Optional.empty();
        }
        else
            return entity;
    }
}