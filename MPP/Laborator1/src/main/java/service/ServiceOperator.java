package service;

import model.Categorie;
import model.Inscriere;
import model.Participant;
import model.Proba;
import repository.IRepositoryInscriere;
import repository.IRepositoryParticipant;
import repository.IRepositoryProba;
import repository.RepositoryException;
import utils.DataChanged;
import utils.EventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

//adauga participanti, face inscrieri
public class ServiceOperator implements Observable<DataChanged> {
    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private IRepositoryParticipant repoParticipant;
    private IRepositoryProba repoProba;
    private IRepositoryInscriere repoInscriere;

    public ServiceOperator(IRepositoryParticipant repoParticipant, IRepositoryProba repoProba, IRepositoryInscriere repoInscriere) {
        this.repoParticipant = repoParticipant;
        this.repoProba = repoProba;
        this.repoInscriere = repoInscriere;
    }

    public Iterable<Participant> filtreazaParticipantiKeyword(String proba,String categorie) {
//        if(proba.equals(""))
//            proba=null;
//        if(categorie.equals(""))
//            categorie=null;

        if(proba==null)
            return repoInscriere.cautaParticipantiDupaCategorie(categorie);
        if(categorie==null)
            return repoInscriere.cautaParticipantiDupaProba(proba);

        return repoInscriere.cautaParticipantDupaProbaCategorie(proba,categorie);
    }

    public Iterable<Proba> listaProbe(){
        return repoProba.findAll();
    }

    public Iterable<Participant> listaParticipanti(){
        return repoParticipant.findAll();
    }

    public Iterable<Categorie> listaCategorii(){
        return repoProba.listaCategorii();
    }

    public Iterable<String> listaProbeNume(){
        return repoProba.listaProbeNume();
    }

    public int nrParticipantiProba(Proba proba){
        return repoInscriere.nrParticipantiProba(proba);
    }

    public int nrParticipantiCategorie(String categorie){
        return repoInscriere.nrParticipantiCategorie(categorie);
    }

    public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe,String usernameOperator){
        for(Proba p:listaProbe){
            if(!verificaCtg(varsta, p))
                throw new RepositoryException("Participantul nu se poate inscrie la aceasta categorie de varsta");
        }
        if(listaProbe.size()>2)
            throw new RepositoryException("Participantul nu se poate inscrie la mai mult de 2 probe");
        int idPartic = repoParticipant.save(new Participant(nume,varsta));
        listaProbe.forEach(pr->repoInscriere.save(new Inscriere(idPartic,pr.getID(),usernameOperator)));
        notifyObservers(new DataChanged(EventType.ADD));
    }

    public void stergeToateInregistrarile(){
        repoInscriere.deleteAll();
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

    private boolean verificaCtg(int varsta, Proba proba){
        String categorie=proba.getCatg().toString();
        String var=categorie.substring(10);
        String[] varste = var.split("_");
        int min=Integer.parseInt(varste[0]);
        int max=Integer.parseInt(varste[1]);
        if(varsta>=min && varsta<=max)
            return true;
        return false;
    }
}
