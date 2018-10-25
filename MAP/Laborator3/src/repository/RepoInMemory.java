package repository;

import domain.HasID;
import repository.CrudRepository;
import validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class RepoInMemory<ID, E extends HasID<ID>> implements CrudRepository<ID, E>{
    private Validator<E> validator;
    protected Map<ID,E> entities;

    public RepoInMemory(Validator<E> validator){
        this.validator=validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if(entity==null) throw new IllegalArgumentException("Nu ai dat parametru");
        validator.validate(entity);
        E temp = entities.get(entity.getID());
        if(temp != null) return entity;
        entities.put(entity.getID(), entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Nu ai dat parametru");
        E temp=entities.get(id);
        if(temp==null) return null;
        entities.remove(id);
        return temp;
    }

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