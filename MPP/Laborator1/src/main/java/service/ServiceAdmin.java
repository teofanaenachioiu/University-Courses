package service;

import utils.DataChanged;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

//adminul adauga probe, operatori
public class ServiceAdmin implements Observable<DataChanged> {
    private List<Observer<DataChanged>> observers=new ArrayList<>();

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
}
