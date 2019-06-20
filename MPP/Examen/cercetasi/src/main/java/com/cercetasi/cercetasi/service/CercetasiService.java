package com.cercetasi.cercetasi.service;

import com.cercetasi.cercetasi.domain.ControlParticipant;
import com.cercetasi.cercetasi.domain.Coordonator;
import com.cercetasi.cercetasi.domain.Participant;
import com.cercetasi.cercetasi.dto.ParticipantDto;
import com.cercetasi.cercetasi.repository.ControlParticipantRepository;
import com.cercetasi.cercetasi.repository.CoordonatorRepository;
import com.cercetasi.cercetasi.repository.ParticipantRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CercetasiService {
    CoordonatorRepository coordonatorRepository;
    ParticipantRepository participantRepository;
    ControlParticipantRepository controlParticipantRepository;
    private static final Logger logger = LogManager.getLogger(CercetasiService.class);

    @Autowired
    CercetasiService(CoordonatorRepository coordonatorRepository,
                     ParticipantRepository participantRepository,
                     ControlParticipantRepository controlParticipantRepository) {
        this.coordonatorRepository = coordonatorRepository;
        this.participantRepository = participantRepository;
        this.controlParticipantRepository = controlParticipantRepository;
    }

    public List<ParticipantDto> getParticipantiControl(String usernameCoord) {
        Coordonator coordonator = this.coordonatorRepository.findById(usernameCoord).get();
        Integer punctControl = coordonator.getPunctControl();
        List<ParticipantDto> participantDtoList = new ArrayList<>();

        if (punctControl.equals(0)) {
            List<Participant> participantList = this.participantRepository.findAll();
            for (Participant participant : participantList) {

                List<ControlParticipant> controlParticipantList = participant.getControlParticipanti()
                        .stream()
                        .sorted(Comparator.comparing(ControlParticipant::getOraSosire))
                        .collect(Collectors.toList());

                if (controlParticipantList.size() != 0) {
                    Integer dimensiune = controlParticipantList.size();
                    ControlParticipant controlParticipant = controlParticipantList.get(dimensiune - 1);
                    participantDtoList.add(participantDtoConverter(controlParticipant));
                }
            }
        } else {
            for(Participant participant: this.participantRepository.findAll()){
                List<ControlParticipant> controlParticipantList = participant.getControlParticipanti()
                        .stream()
                        .sorted(Comparator.comparing(ControlParticipant::getOraSosire))
                        .collect(Collectors.toList());
                if (controlParticipantList.size() != 0) {
                    Integer dimensiune = controlParticipantList.size();
                    ControlParticipant controlParticipant = controlParticipantList.get(dimensiune - 1);
                    if(controlParticipant.getCoordonatorMapat().getPunctControl().equals(coordonator.getPunctControl())){
                        participantDtoList.add(participantDtoConverter(controlParticipant));
                    }
                }
            }
        }
        return participantDtoList;
    }

    private Coordonator getNextCoordonator(Integer point) {
        List<Coordonator> coordonators = this.coordonatorRepository.findAll();
        for (Coordonator c : coordonators) {
            if (c.getPunctControl().equals(point))
                return c;
        }
        return null;
    }

    public void participantPassed(Integer idControl, Long oraSosirii) {
        ControlParticipant controlParticipant = this.controlParticipantRepository.findById(idControl).get();
        Integer nextPoint = controlParticipant.getCoordonatorMapat().getPunctControl() + 1;

        Coordonator nextCoordonator = this.getNextCoordonator(nextPoint);
        if(nextCoordonator!=null) {
            this.controlParticipantRepository.save(new ControlParticipant(oraSosirii,
                    nextCoordonator, controlParticipant.getParticipantMapat()));
        }
    }

    private ParticipantDto participantDtoConverter(ControlParticipant controlParticipant) {
        return ParticipantDto.builder()
                .id(controlParticipant.getId())
                .nume(controlParticipant.getParticipantMapat().getNume())
                .oraSosirii(controlParticipant.getOraSosire())
                .ultimulPunctDeControl(controlParticipant.getCoordonatorMapat().getPunctControl())
                .build();
    }
}
