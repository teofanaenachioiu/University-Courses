package repository;

import domain.Tema;
import validator.ValidatorTema;

import java.util.ArrayList;
import java.util.List;

public class RepoTemeInMemory implements  CrudRepository<Integer,Tema>{
    private List<Tema> repo=new ArrayList<Tema>();
    private ValidatorTema val=new ValidatorTema();

    @Override
    public Tema findOne(Integer integer) {
        if(integer==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        for (Tema t:repo) {
            if(t.getID().equals(integer)){
                return t;
            }
        }
        return null;
    }

    @Override
    public Iterable<Tema> findAll() {
        return this.repo;
    }

    @Override
    public Tema save(Tema entity) throws ValidationException {
        if(entity==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        if(findOne(entity.getID())!=null) return entity;
        else {
            val.validate(entity);
            repo.add(entity);
            return null;
        }
    }

    @Override
    public Tema delete(Integer integer) {
        if(integer==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        else{
            Tema t=findOne(integer);
            if(t==null) return null;
            else{
                repo.remove(t);
                return t;
            }
        }
    }

    @Override
    public Tema update(Tema entity) {
        if(entity==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        Tema t=findOne(entity.getID());
        if(t==null) return entity;
        else {
            val.validate(t);
            repo.remove(t);
            repo.add(entity);
            return null;
        }
    }
}
