package services;

import model.Categorie;
import model.Participant;
import model.Proba;
import model.User;
import model.dto.ProbaDTO;

import java.util.List;


public interface IServer {
    public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe, String usernameOperator) throws MyAppException;

    public Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie) throws MyAppException;

    public Iterable<Proba> listaProbe() throws MyAppException;

    public ProbaDTO [] listaProbeDTO() throws MyAppException;

    public Iterable<Participant> listaParticipanti() throws MyAppException;

    public Iterable<Categorie> listaCategorii() throws MyAppException;

    public Iterable<String> listaProbeNume() throws MyAppException;

    public int nrParticipantiProba(Proba proba) throws MyAppException;

//    public int nrParticipantiCategorie(String categorie);

//    public boolean verificaCtg(int varsta, Proba proba);

    public User cauta(String username) throws MyAppException;

    public void login(String username, String password, IObserver client) throws MyAppException;

    public void logout(User user) throws MyAppException;

    //public User createUser(String username, String parola) throws PasswordStorage.CannotPerformOperationException ;

    //public User createUser() throws PasswordStorage.CannotPerformOperationException ;

}
