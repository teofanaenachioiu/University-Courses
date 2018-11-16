package sem4.repository;

import sem4.domain.HasID;
import sem4.domain.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends HasID<ID>> implements CrudRepository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getID()) != null) {
            return entity;
        }
        entities.put(entity.getID(),entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        return null;
    }

    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        if(entities.get(entity.getID()) != null) {
            entities.put(entity.getID(),entity);
            return null;
        }
        return entity;

    }

}
