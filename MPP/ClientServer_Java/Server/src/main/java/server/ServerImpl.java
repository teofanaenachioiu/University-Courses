package server;

import model.Proba;
import model.User;
import repository.IRepositoryInscriere;
import repository.IRepositoryParticipant;
import repository.IRepositoryProba;
import repository.IRepositoryUser;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerImpl implements IServer {

    private IRepositoryUser userRepository;
    private IRepositoryParticipant participantRepository;
    private IRepositoryProba probaRepository;
    private IRepositoryInscriere inscriereRepository;

    private Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public ServerImpl(IRepositoryUser uRepo, IRepositoryParticipant pRepo, IRepositoryProba prRepo, IRepositoryInscriere iRepo) {

        this.userRepository = uRepo;
        this.probaRepository = prRepo;
        this.participantRepository = pRepo;
        this.inscriereRepository = iRepo;
        loggedClients = new ConcurrentHashMap<>();
        ;
    }

    @Override
    public Iterable<Proba> listaProbe() throws MyAppException{
        return probaRepository.findAll();
    }

    @Override
    public int nrParticipantiProba(Proba proba) throws MyAppException {
        return inscriereRepository.nrParticipantiProba(proba);
    }

    @Override
    public synchronized User cauta(String username) throws MyAppException{
        return userRepository.findOne(username);
    }

    @Override
    public synchronized void login(String username, String password, IObserver client) throws MyAppException {
        User user = userRepository.findOne(username);

        if (user != null) {
            if (loggedClients.get(user.getID()) != null)
                throw new MyAppException("User already logged in.");
            loggedClients.put(user.getID(), client);
            String userHash = user.getHash();
            try {
                if (!PasswordStorage.verifyPassword(password, userHash))
                    throw new MyAppException("Invalid password.");
            } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
                throw new MyAppException("Authentication failed.");
            }
        }
        else{
            throw  new MyAppException("No user");
        }
    }

    @Override
    public void logout(User user) throws MyAppException {
        IObserver localClient=loggedClients.remove(user.getID());
        if (localClient==null)
            throw new MyAppException("User "+user.getID()+" is not logged in.");
    }
}
