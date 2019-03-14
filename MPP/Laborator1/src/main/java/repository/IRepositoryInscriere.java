package repository;

import javafx.util.Pair;
import model.Inscriere;
import model.Participant;

public interface IRepositoryInscriere extends IRepository<Pair<Integer, Integer>, Inscriere> {
    public Iterable<Inscriere> findProbeDupaParticipant(Integer id);

    public Iterable<Participant> cautaParticipantiDupaCategorie(String categorie);
    public Iterable<Participant> cautaParticipantiDupaProba(String proba);
    public Iterable<Participant> cautaParticipantDupaProbaCategorie(String proba, String categorie);
    public int nrParticipantiProba(String proba);
    public int nrParticipantiCategorie(String categorie);

}