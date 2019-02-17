package repository;

import domain.HasID;
import domain.ValidationException;
import domain.Validator;

import java.util.HashMap;

public abstract class AbstractRepository<ID, E extends HasID<ID>> implements Repository<ID,E> {
    private HashMap<ID, E> entities;
    private Validator<E> vali;

    public AbstractRepository(Validator<E> vali) {
        entities=new HashMap<ID, E>();
        this.vali = vali;
    }

    @Override
    public long size() {
        return entities.size();
    }

    @Override
    public E save(E entity) throws ValidationException {
        vali.validate(entity);
        if (entities.containsKey(entity.getId())){
            return entities.get(entity.getId());
        }
        entities.put(entity.getId(),entity);
        return null;
    }

    @Override
    public E update(E entity) throws ValidationException {
        if (entities.containsKey(entity.getId())){
            vali.validate(entity);
            return entities.put(entity.getId(),entity);
        }
        return null;
    }

    @Override
    public E delete(ID id) {
        return entities.remove(id);
    }

    @Override
    public E findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
}