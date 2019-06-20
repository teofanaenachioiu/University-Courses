package server;

import model.*;
import model.dto.DTOutils;
import model.dto.ProbaDTO;
import repository.IRepositoryInscriere;
import repository.IRepositoryParticipant;
import repository.IRepositoryProba;
import repository.IRepositoryUser;
import services.IServer;
import services.MyAppException;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.*;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class ServerImpl implements IServer {

    private static final String EXCHANGE_NAME = "logs";

    private IRepositoryUser userRepository;
    private IRepositoryParticipant participantRepository;
    private IRepositoryProba probaRepository;
    private IRepositoryInscriere inscriereRepository;

    private Set<String> loggedClients;
    private final int defaultThreadsNo = 5;

    public ServerImpl(IRepositoryUser uRepo, IRepositoryParticipant pRepo, IRepositoryProba prRepo, IRepositoryInscriere iRepo) {

        this.userRepository = uRepo;
        this.probaRepository = prRepo;
        this.participantRepository = pRepo;
        this.inscriereRepository = iRepo;
        loggedClients = new ConcurrentSkipListSet<>();
        ;
    }

    private synchronized boolean verificaCtg(int varsta, Proba proba) {
        String categorie = proba.getCatg().toString();
        String var = categorie.substring(10);
        String[] varste = var.split("_");
        int min = Integer.parseInt(varste[0]);
        int max = Integer.parseInt(varste[1]);
        return varsta >= min && varsta <= max;
    }

//    private void notifyClients() throws MyAppException {
//        System.out.println("Notify");
//
//        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
//        for (String username : loggedClients) {
//            IObserver client = loggedClients.get(username);
//            if (client != null)
//                executor.execute(() -> {
//                    try {
//                        System.out.println("Notifying [" + username + "]");
//                        client.update();
//                    } catch (MyAppException e) {
//                        System.err.println("Error notifying " + e);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                });
//        }
//
//        executor.shutdown();
//    }

//    @Override
//    public synchronized void inscriereParticipant(String nume, int varsta, Proba[] listaProbe, String usernameOperator) throws MyAppException {
//        for (Proba p : listaProbe) {
//            if (!verificaCtg(varsta, p))
//                throw new MyAppException("Participantul nu se poate inscrie la aceasta categorie de varsta");
//        }
//        int dim = listaProbe.length;
//        if (dim > 2)
//            throw new MyAppException("Participantul nu se poate inscrie la mai mult de 2 probe");
//        int idPartic = participantRepository.save(new Participant(nume, varsta));
//        for (Proba aListaProbe : listaProbe) {
//            inscriereRepository.save(new Inscriere(idPartic, aListaProbe.getID(), usernameOperator));
//
//        }
//        notifyClients();
//    }

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
        initSendMessage();
    }

    private void initSendMessage() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "update";

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return userRepository.findOne(username);
    }

    @Override
    public synchronized void login(String username, String password) throws MyAppException {
        User user = userRepository.findOne(username);

        if (user != null) {
            if (loggedClients.contains(user.getID()))
                throw new MyAppException("User already logged in.");

            String userHash = user.getHash();
            try {
                if (!PasswordStorage.verifyPassword(password, userHash))
                    throw new MyAppException("Invalid password.");
                else {
                    loggedClients.add(user.getID());
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
        if (!loggedClients.remove(user.getID()))
            throw new MyAppException("User " + user.getID() + " is not logged in.");
    }
}
