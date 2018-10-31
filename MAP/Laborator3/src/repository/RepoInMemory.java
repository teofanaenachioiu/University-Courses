package repository;

import domain.HasID;
import repository.CrudRepository;
import validator.Validator;

import java.util.HashMap;
import java.util.Map;

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
    public E findOne(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
        return entities.get(id);
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
    public E save(E entity) throws ValidationException {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        validator.validate(entity);
        E temp = entities.get(entity.getID());
        if(temp != null) return entity;
        entities.put(entity.getID(), entity);
        return null;
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
    public E delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
        E temp=entities.get(id);
        if(temp==null) return null;
        entities.remove(id);
        return temp;
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
    public E update(E entity) {
        if(entity==null)
            throw new IllegalArgumentException("Nu ai dat parametru");
        validator.validate(entity);
        if(entities.get(entity.getID())!=null){
            entities.put(entity.getID(),entity);
            return null;
        }
        return entity;
    }
}