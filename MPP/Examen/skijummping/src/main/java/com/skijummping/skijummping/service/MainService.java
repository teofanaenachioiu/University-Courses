package com.skijummping.skijummping.service;

import com.skijummping.skijummping.domain.Participant;
import com.skijummping.skijummping.domain.StatusParticipant;
import com.skijummping.skijummping.domain.Utilizator;
import com.skijummping.skijummping.repository.ParticipantRepository;
import com.skijummping.skijummping.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainService {
    ParticipantRepository participantRepository;

    @Autowired
    MainService(ParticipantRepository participantRepository, UtilizatorRepository utilizatorRepository){
        this.participantRepository = participantRepository;
    }

    public List<Participant> getAllParticipanti(){
        return this.participantRepository.findAll();
    }

    public List<Participant> getAllParticipantiStatus(StatusParticipant statusParticipant){
        return this.participantRepository.getParticipantByStatus(statusParticipant);
    }

    @Transactional
    public Participant adaugaPuncte(Participant participant){
        Participant participantFound = participantRepository.getOne(participant.getId());

        participantFound.setArePunctePentruAterizare(participant.isArePunctePentruAterizare());
        participantFound.setArePunctePentruStil(participant.isArePunctePentruStil());
        participantFound.setArePunctePentruLungime(participant.isArePunctePentruLungime());
        participantFound.setNumarPunctePartial(participant.getNumarPunctePartial());

        if(participant.isArePunctePentruAterizare()
                && participant.isArePunctePentruLungime()
                && participant.isArePunctePentruStil()){
            if(participant.getStatusParticipant().equals(StatusParticipant.FIRST_JUMP)){
                participantFound.setStatusParticipant(StatusParticipant.FINISHED);
            }
            else{
                participantFound.setStatusParticipant(StatusParticipant.FIRST_JUMP);
                participantFound.setArePunctePentruAterizare(false);
                participantFound.setArePunctePentruStil(false);
                participantFound.setArePunctePentruLungime(false);
            }
            participantFound.setNumarPuncte(participant.getNumarPunctePartial());
        }

        return participantFound;
    }
}
