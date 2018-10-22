package repository;

import domain.Tema;

import java.util.ArrayList;
import java.util.List;

public class RepoTemeInMemory implements  CrudRepository<Integer,Tema>{
        private List<Tema> repo=new ArrayList<Tema>();

    @Override
    public Tema findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Tema> findAll() {
        return null;
    }

    @Override
    public Tema save(Tema entity) throws ValidationException {
        return null;
    }

    @Override
    public Tema delete(Integer integer) {
        return null;
    }

    @Override
    public Tema update(Tema entity) {
        return null;
    }
}
