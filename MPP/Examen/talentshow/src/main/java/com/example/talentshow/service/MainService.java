package com.example.talentshow.service;

import com.example.talentshow.domain.NrJurat;
import com.example.talentshow.domain.Participant;
import com.example.talentshow.domain.StatusParticipant;
import com.example.talentshow.repository.ParticipantRepository;
import com.example.talentshow.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
    private ParticipantRepository participantRepository;
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    MainService(ParticipantRepository participantRepository, UtilizatorRepository utilizatorRepository) {
        this.participantRepository = participantRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Participant> getAllParticipanti() {
        return this.participantRepository.findAll();
    }

    public List<Participant> getAllParticipantiJurat(NrJurat nrJurat) {
        if (nrJurat.equals(NrJurat.JURAT1)) {
            return this.participantRepository.findAll()
                    .stream()
                    .filter(x -> !x.getNumarPuncteJurat1().equals(0))
                    .collect(Collectors.toList());
        }
        if (nrJurat.equals(NrJurat.JURAT2)) {
            return this.participantRepository.findAll()
                    .stream()
                    .filter(x -> !x.getNumarPuncteJurat2().equals(0))
                    .collect(Collectors.toList());
        }
        if (nrJurat.equals(NrJurat.JURAT3)) {
            return this.participantRepository.findAll()
                    .stream()
                    .filter(x -> !x.getNumarPuncteJurat3().equals(0))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public void adaugaPuncte(Participant participant) {
        Participant participantFound = this.participantRepository.getOne(participant.getId());
        participantFound.setNumarPuncteJurat1(participant.getNumarPuncteJurat1());
        participantFound.setNumarPuncteJurat2(participant.getNumarPuncteJurat2());
        participantFound.setNumarPuncteJurat3(participant.getNumarPuncteJurat3());

        if (participantFound.getStatusParticipant().equals(StatusParticipant.NO_RESULTS)) {
            participantFound.setStatusParticipant(StatusParticipant.PENDING);
        }
        if (!participantFound.getNumarPuncteJurat1().equals(0) &&
                !participantFound.getNumarPuncteJurat2().equals(0) &&
                !participantFound.getNumarPuncteJurat3().equals(0)) {
            participantFound.setStatusParticipant(StatusParticipant.COMPLETED);
        }
    }

}
