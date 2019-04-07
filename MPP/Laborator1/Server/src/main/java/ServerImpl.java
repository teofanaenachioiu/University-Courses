import model.*;
import repository.*;
import services.IObserver;
import services.IServer;
import utils.PasswordStorage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.PasswordStorage.createHash;


public class ServerImpl implements IServer {

    private IRepositoryUser userRepository;
    private IRepositoryParticipant participantRepository;
    private IRepositoryProba probaRepository;
    private IRepositoryInscriere inscriereRepository;

    private Map<String, IObserver> loggedClients;

    public ServerImpl(IRepositoryUser uRepo, IRepositoryParticipant pRepo, IRepositoryProba prRepo, IRepositoryInscriere iRepo) {

        this.userRepository= uRepo;
        this.probaRepository=prRepo;
        this.participantRepository =pRepo;
        this.inscriereRepository = iRepo;
        loggedClients=new ConcurrentHashMap<>();;
    }

    @Override
    public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe, String usernameOperator) {
        for(Proba p:listaProbe){
            if(!verificaCtg(varsta, p))
                throw new RepositoryException("Participantul nu se poate inscrie la aceasta categorie de varsta");
        }
        if(listaProbe.size()>2)
            throw new RepositoryException("Participantul nu se poate inscrie la mai mult de 2 probe");
        int idPartic = participantRepository.save(new Participant(nume,varsta));
        listaProbe.forEach(pr->inscriereRepository.save(new Inscriere(idPartic,pr.getID(),usernameOperator)));
    }

    @Override
    public Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie) {
        if(proba==null)
            return inscriereRepository.cautaParticipantiDupaCategorie(categorie);
        if(categorie==null)
            return inscriereRepository.cautaParticipantiDupaProba(proba);

        return inscriereRepository.cautaParticipantDupaProbaCategorie(proba,categorie);
    }

    @Override
    public Iterable<Proba> listaProbe() {
        return probaRepository.findAll();
    }

    @Override
    public Iterable<Participant> listaParticipanti() {
        return participantRepository.findAll();
    }

    @Override
    public Iterable<Categorie> listaCategorii() {
        return probaRepository.listaCategorii();
    }

    @Override
    public Iterable<String> listaProbeNume() {
        return probaRepository.listaProbeNume();
    }

    @Override
    public int nrParticipantiProba(Proba proba) {
        return inscriereRepository.nrParticipantiProba(proba);
    }

    @Override
    public int nrParticipantiCategorie(String categorie) {
        return inscriereRepository.nrParticipantiCategorie(categorie);
    }

    @Override
    public void stergeToateInregistrarile() {
        inscriereRepository.deleteAll();
    }

    @Override
    public boolean verificaCtg(int varsta, Proba proba) {
        String categorie=proba.getCatg().toString();
        String var=categorie.substring(10);
        String[] varste = var.split("_");
        int min=Integer.parseInt(varste[0]);
        int max=Integer.parseInt(varste[1]);
        return varsta >= min && varsta <= max;
    }

    @Override
    public User createUser(String username, String parola) throws PasswordStorage.CannotPerformOperationException {
        String hash= createHash(parola);
        User user=new User(username,hash, TipUser.OPERATOR);
        userRepository.save(user);
        return user;
    }

    @Override
    public User createUser() throws PasswordStorage.CannotPerformOperationException {
        String hash= createHash("admin");
        User user=new User("admin",hash, TipUser.ADMIN);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean verificareParola(String username, String password) {
        User user = userRepository.findOne(username);

        if (user!=null) {
            String userHash = user.getHash();
            try {
                return PasswordStorage.verifyPassword(password, userHash);
            } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public User cauta(String username) {
        User entity= this.userRepository.findOne(username);
        return entity;
    }
}
