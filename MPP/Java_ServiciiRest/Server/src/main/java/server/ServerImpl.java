package server;

import model.*;
import model.dto.DTOutils;
import model.dto.ProbaDTO;
import repository.*;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerImpl implements IServer {

    private UserRepository userRepository;
    private IRepositoryParticipant participantRepository;
    private IRepositoryProba probaRepository;
    private IRepositoryInscriere inscriereRepository;

    private Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public ServerImpl(UserRepository uRepo, IRepositoryParticipant pRepo, IRepositoryProba prRepo, IRepositoryInscriere iRepo) {

        this.userRepository = uRepo;
        this.probaRepository = prRepo;
        this.participantRepository = pRepo;
        this.inscriereRepository = iRepo;
        loggedClients = new ConcurrentHashMap<>();
    }

    private synchronized boolean verificaCtg(int varsta, Proba proba) {
        String categorie = proba.getCatg().toString();
        String var = categorie.substring(10);
        String[] varste = var.split("_");
        int min = Integer.parseInt(varste[0]);
        int max = Integer.parseInt(varste[1]);
        return varsta >= min && varsta <= max;
    }

    private void notifyClients() throws MyAppException {
        System.out.println("Notify");

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (String username : loggedClients.keySet()) {
            IObserver client = loggedClients.get(username);
            if (client != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + username + "]");
                        client.update();
                    } catch (MyAppException e) {
                        System.err.println("Error notifying " + e);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

        executor.shutdown();
    }

    @Override
    public synchronized void inscriereParticipant(String nume, int varsta, Proba[] listaProbe, String usernameOperator) throws MyAppException {
        for (Proba p : listaProbe) {
            if (!verificaCtg(varsta, p))
                throw new MyAppException("Participantul nu se poate inscrie la aceasta categorie de varsta");
        }
        int dim = listaProbe.length;
        if (dim > 2)
            throw new MyAppException("Participantul nu se poate inscrie la mai mult de 2 probe");
        int idPartic = participantRepository.save(new Participant(nume, varsta));
        for (Proba aListaProbe : listaProbe) {
            inscriereRepository.save(new Inscriere(idPartic, aListaProbe.getID(), usernameOperator));

        }
        notifyClients();
    }

    @Override
    public synchronized Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie) {
        if (proba == null)
            return inscriereRepository.cautaParticipantiDupaCategorie(categorie);
        if (categorie == null)
            return inscriereRepository.cautaParticipantiDupaProba(proba);

        return inscriereRepository.cautaParticipantDupaProbaCategorie(proba, categorie);
    }

    @Override
    public synchronized Iterable<Proba> listaProbe() throws MyAppException {
        return probaRepository.findAll();
    }

    @Override
    public synchronized ProbaDTO[] listaProbeDTO() throws MyAppException {
        Iterable<Proba> pr = this.listaProbe();

        int nr = 0;
        for (Proba ignored : pr) {
            nr += 1;
        }

        Proba[] probe = new Proba[nr];
        int[] nrPartic = new int[nr];

        int i = 0;
        for (Proba p : pr) {
            probe[i] = p;
            nrPartic[i] = this.nrParticipantiProba(p);
            i += 1;
        }
        System.out.println(probe.length);
        return DTOutils.getDTO(probe, nrPartic);
    }

    @Override
    public synchronized Iterable<Participant> listaParticipanti() throws MyAppException {
        return participantRepository.findAll();
    }

    @Override
    public synchronized Iterable<Categorie> listaCategorii() throws MyAppException {
        return probaRepository.listaCategorii();
    }

    @Override
    public synchronized Iterable<String> listaProbeNume() throws MyAppException {
        return probaRepository.listaProbeNume();
    }

    @Override
    public synchronized int nrParticipantiProba(Proba proba) throws MyAppException {
        return inscriereRepository.nrParticipantiProba(proba);
    }

    @Override
    public synchronized User cauta(String username) throws MyAppException {
        userRepository.initialize();
        User user = userRepository.findOne(username);
        userRepository.close();
        return user;
    }

    @Override
    public synchronized User[] cauta(String username, TipUser tip) throws MyAppException {
        userRepository.initialize();
        User[] users = new User[0];
        if (username == null || username.equals("")) {
            users = userRepository.findOneByTip(tip.toString());
        } else {
            User user = userRepository.findOne(username);
            if (user.getTip().equals(tip)) {
                users = new User[1];
                users[0] = user;
            }
        }
        userRepository.close();
        return users;
    }

    @Override
    public synchronized void login(String username, String password, IObserver client) throws MyAppException {
        userRepository.initialize();
        User user = userRepository.findOne(username);
        System.out.println(user);
        userRepository.close();
        if (user != null) {
            if (loggedClients.get(user.getID()) != null)
                throw new MyAppException("User already logged in.");

            String userHash = user.getHash();
            try {
                if (!PasswordStorage.verifyPassword(password, userHash))
                    throw new MyAppException("Invalid password.");
                else {
                    loggedClients.put(user.getID(), client);
                    System.out.println("Am intrat la " + username);
                }
            } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
                throw new MyAppException("Authentication failed.");
            }
        } else {
            throw new MyAppException("No user");
        }
    }

    @Override
    public synchronized void logout(User user) throws MyAppException {
        IObserver localClient = loggedClients.remove(user.getID());
        if (localClient == null) {
            throw new MyAppException("User " + user.getID() + " is not logged in.");
        }
        userRepository.close();
    }

    @Override
    public User addUser(String username, String parola, TipUser tip) throws MyAppException {
        userRepository.initialize();
        String parolaHash;
        try {
            parolaHash = PasswordStorage.createHash(parola);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new MyAppException("Nu pot sa fac hash-ul");
        }
        User user = new User(username, parolaHash, tip);
        String usrnam = userRepository.save(user);
        if (usrnam == null) {
            throw new MyAppException("Username existent!");
        }
        userRepository.close();
        notifyClients();
        return user;
    }

    @Override
    public User[] listaUseri() {
        userRepository.initialize();
        Iterable<User> users = userRepository.findAll();
        userRepository.close();
        int nr = 0;
        for (User ignored : users) {
            nr += 1;
        }

        User[] userDTO = new User[nr];

        int i = 0;
        for (User user : users) {
            userDTO[i] = user;
            i += 1;
        }

        return userDTO;
    }

    @Override
    public User deleteUser(String id) throws MyAppException {
        userRepository.initialize();
        User user = userRepository.findOne(id);
        if (user == null) throw new MyAppException("Nu exista username-ul");
        userRepository.delete(id);
        userRepository.close();
        notifyClients();
        return user;
    }

    @Override
    public void updateUser(User user) throws MyAppException {
        userRepository.initialize();
        if (user.getHash() == null || user.getHash().equals("")) {
            User us = userRepository.findOne(user.getID());
            user.setHash(us.getHash());
        } else {
            try {
                user.setHash(PasswordStorage.createHash(user.getHash()));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
            }
        }
        userRepository.update(user.getID(), user);
        notifyClients();
        userRepository.close();


    }
}
