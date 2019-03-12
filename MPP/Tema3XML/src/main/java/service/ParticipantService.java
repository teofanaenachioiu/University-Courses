package service;

import model.Participant;
import repository.IRepository;

import java.io.Serializable;

public class ParticipantService implements Serializable {
    IRepository<String, Participant> repo;

    public ParticipantService(IRepository<String, Participant> repo) {
        this.repo = repo;
    }

    public ParticipantService() {
        this.repo = repo;
    }

    public IRepository<String, Participant> getRepo() {
        return repo;
    }

    public void setRepo(IRepository<String, Participant> repo) {
        this.repo = repo;
    }

    public int size(){
        return repo.size();
    }
}
