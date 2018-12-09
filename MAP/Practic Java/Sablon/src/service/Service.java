package service;

import domain.Entity1;
import domain.Entity12;
import domain.Entity2;
import javafx.util.Pair;
import repository.RepositoryXMLEntity1;
import repository.RepositoryXMLEntity12;
import repository.RepositoryXMLEntity2;
import utils.DataChanged;
import utils.EventType;
import utils.Observable;
import utils.Observer;
import validation.ValidatorEntity1;
import validation.ValidatorEntity12;
import validation.ValidatorEntity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Service implements Observable<DataChanged> {
    RepositoryXMLEntity1 repositoryXMLEntity1=new RepositoryXMLEntity1("./src/data/entity1.xml",new ValidatorEntity1());
    RepositoryXMLEntity2 repositoryXMLEntity2=new RepositoryXMLEntity2("./src/data/entity2.xml",new ValidatorEntity2());
    RepositoryXMLEntity12 repositoryXMLEntity12=new RepositoryXMLEntity12("./src/data/entity12.xml",new ValidatorEntity12());
    private List<Observer<DataChanged>> observers=new ArrayList<>();


    public boolean addEntity1(Entity1 entity1){
        notifyObservers(new DataChanged(EventType.ADD));
        return repositoryXMLEntity1.save(entity1).isPresent();
    }

    public boolean deleteEntity1(String id){
        notifyObservers(new DataChanged(EventType.DELETE));
        return repositoryXMLEntity1.delete(id).isPresent();
    }

    public boolean updateEntity1(Entity1 entity1){
        notifyObservers(new DataChanged(EventType.UPDATE));
        return !repositoryXMLEntity1.update(entity1).isPresent();
    }

    public Entity1 findEntity1(String id){
        if(repositoryXMLEntity1.findOne(id).isPresent()) return repositoryXMLEntity1.findOne(id).get();
        return null;
    }

    public Iterable<Entity1> getAllEntity1(){
        return repositoryXMLEntity1.findAll();
    }

    public boolean addEntity12(Entity12 entity){
        return repositoryXMLEntity12.save(entity).isPresent();
    }

    public boolean deleteEntity12(String id1, Integer id2){
        Entity1 e1=this.findEntity1(id1);
        if(e1==null) return false;
        Optional<Entity2> e2=repositoryXMLEntity2.findOne(id2);
        if(!e2.isPresent()) return false;
        return repositoryXMLEntity12.delete(new Pair(e1,e2)).isPresent();
    }

    public Entity12 findEntity12(String id1,Integer id2){
        Entity1 e1=this.findEntity1(id1);
        if(e1==null) return null;
        Optional<Entity2> e2=repositoryXMLEntity2.findOne(id2);
        if(!e2.isPresent()) return null;
        if( repositoryXMLEntity12.findOne(new Pair(e1,e2)).isPresent())
            return repositoryXMLEntity12.findOne(new Pair<Entity1,Entity2>(e1,e2.get())).get();
        return null;
    }

    public Iterable<Entity12> getAllEntity12(){
        return repositoryXMLEntity12.findAll();
    }

    // ----------------------------- filtrare -----------------------------------
    private <T> Iterable <T> filter(Iterable <T> list, Predicate<T> cond)
    {
        List<T> rez=new ArrayList<>();
        list.forEach((T x)->{if (cond.test(x)) rez.add(x);});
        return rez;
    }
    // --------------------------------------------------------------------------


    // ----------------------------- observer ------------------------------------
    @Override
    public void addObserver(Observer<DataChanged> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<DataChanged> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(DataChanged t) {
        observers.forEach(o->o.update(t));
    }
    // ---------------------------------------------------------------------------




}
