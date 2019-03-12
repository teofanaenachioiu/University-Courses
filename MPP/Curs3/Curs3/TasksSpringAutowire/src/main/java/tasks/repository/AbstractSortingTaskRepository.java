package tasks.repository;

import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grigo on 11/14/16.
 */
public abstract class AbstractSortingTaskRepository implements SortingTaskRepository {
    protected Map<Integer, SortingTask> entities;
    protected Validator<SortingTask> validator;

    public AbstractSortingTaskRepository(Validator<SortingTask> valid ){
        entities=new HashMap<>();
        validator=valid;
    }
    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public void update(Integer id, SortingTask entity) {
        try {
            validator.validate(entity);
        }catch(ValidationException ex){
            System.err.println("Entitatea " +entity+ "nu este valida");
            throw  ex;
        }

        if (!(entities.get(id)==null)) {
            if (!id.equals(entity.getId()))
                if (entities.get(entity.getId())!=null)
                    throw new RepositoryException("Id "+entity.getId()+" already exists!!");
            entities.put(entity.getId(), entity);
            System.out.println("Entitate modificata " + entity);
        }else
            throw new RepositoryException("Id "+id +" does not  exists");
    }

    @Override
    public void save(SortingTask entity) {
        try {
            validator.validate(entity);
        }catch(ValidationException ex){
            System.err.println("Entitatea " +entity+ "nu este valida");
            throw  ex;
        }

        // setEntityId(entity);
        Integer id=entity.getId();
        if (entities.get(id)==null) {
            entities.put(id, entity);
        }else
            throw new RepositoryException("Id already exists"+id);

    }

    @Override
    public void delete(Integer id) {
        entities.remove(id);
        System.out.println("Entity deleted "+id);
    }

    @Override
    public SortingTask findOne(Integer id) {
        SortingTask res=entities.get(id);
        if (res!=null)
            return res;
        throw new RepositoryException("Id not found "+id);
    }

    @Override
    public Iterable<SortingTask> findAll() {
        return entities.values();
    }

    @Override
    public Iterable<SortingTask> filterBy(SortingOrder order) {
        ArrayList<SortingTask> result=new ArrayList<>();
        for (SortingTask st: entities.values()) {
            if (st.getOrder().compareTo(order)==0)
                result.add(st);

        }
        return result;
    }

    @Override
    public Iterable<SortingTask> filterBy(SortingAlgorithm algo) {
        ArrayList<SortingTask> result=new ArrayList<>();
        for (SortingTask st: entities.values()) {
            if (st.getAlg().compareTo(algo)==0)
                result.add(st);

        }
        return result;
    }
}

