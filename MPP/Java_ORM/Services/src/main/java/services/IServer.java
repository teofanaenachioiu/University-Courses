package services;

import model.*;
import model.dto.ProbaDTO;

import java.util.List;


public interface IServer {
    public void inscriereParticipant(String nume, int varsta, Proba[] listaProbe, String usernameOperator) throws MyAppException;

    public Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie) throws MyAppException;

    public Iterable<Proba> listaProbe() throws MyAppException;

    public ProbaDTO[] listaProbeDTO() throws MyAppException;

    public Iterable<Participant> listaParticipanti() throws MyAppException;

    public Iterable<Categorie> listaCategorii() throws MyAppException;

    public Iterable<String> listaProbeNume() throws MyAppException;

    public int nrParticipantiProba(Proba proba) throws MyAppException;

    public User cauta(String username) throws MyAppException;

    public User[] cauta(String username, TipUser tip) throws MyAppException;

    public void login(String username, String password, IObserver client) throws MyAppException;

    public void logout(User user) throws MyAppException;

    public User addUser(String username, String parola, TipUser tip) throws MyAppException;

    public User[] listaUseri();

    public User deleteUser(String id) throws MyAppException;

    public void updateUser(User user) throws MyAppException;

}
