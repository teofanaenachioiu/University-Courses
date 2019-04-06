package service;

import model.TipUser;
import model.User;
import repository.IRepositoryUser;
import utils.DataChanged;
import utils.Observable;
import utils.Observer;
import utils.PasswordStorage;

import java.util.ArrayList;
import java.util.List;

import static utils.PasswordStorage.createHash;

//adminul adauga probe, operatori
public class ServiceAdmin implements Observable<DataChanged> {
    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private IRepositoryUser repository;

    public ServiceAdmin(IRepositoryUser repository) {
        this.repository = repository;
    }

    public User createUser(String username, String parola) throws PasswordStorage.CannotPerformOperationException {
        String hash= createHash(parola);
        User user=new User(username,hash, TipUser.OPERATOR);
        repository.save(user);
        return user;
    }

    public User createUser() throws PasswordStorage.CannotPerformOperationException {
        String hash= createHash("admin");
        User user=new User("admin",hash, TipUser.ADMIN);
        repository.save(user);
        return user;
    }

    public boolean verificareParola(String username, String password) {
        User user = repository.findOne(username);

        if (user!=null) {
            String userHash = user.getHash();
            try {
                return PasswordStorage.verifyPassword(password, userHash);
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
            } catch (PasswordStorage.InvalidHashException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public User cauta(String username){
        User entity= repository.findOne(username);
        return entity;
    }


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
