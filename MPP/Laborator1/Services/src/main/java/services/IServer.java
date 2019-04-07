package services;

import model.*;
import utils.PasswordStorage;

import java.util.List;


public interface IServer {
    public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe, String usernameOperator);

    public Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie);

    public Iterable<Proba> listaProbe();

    public Iterable<Participant> listaParticipanti();

    public Iterable<Categorie> listaCategorii();

    public Iterable<String> listaProbeNume();

    public int nrParticipantiProba(Proba proba);

    public int nrParticipantiCategorie(String categorie);

    public boolean verificaCtg(int varsta, Proba proba);

    public User createUser(String username, String parola) throws PasswordStorage.CannotPerformOperationException ;

    public User createUser() throws PasswordStorage.CannotPerformOperationException ;

    public boolean verificareParola(String username, String password);

    public User cauta(String username);
}
