package com.example.talentshow.controller;

import com.example.talentshow.domain.NrJurat;
import com.example.talentshow.domain.Participant;
import com.example.talentshow.domain.StatusParticipant;
import com.example.talentshow.dto.ParticipantDto;
import com.example.talentshow.service.MainService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/talentshow")
public class MainController {
    private static final String EXCHANGE_NAME = "logs";
    private MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getParticipati(){
        return new ResponseEntity<>(mainService.getAllParticipanti(), HttpStatus.OK);
    }

    @GetMapping(value = "/{jurat}")
    public ResponseEntity<?> getParticipantiJurat(@PathVariable String jurat){
        NrJurat nrJurat = NrJurat.valueOf(jurat);
        List<Participant> listaParticipanti = mainService.getAllParticipantiJurat(nrJurat);
        if(listaParticipanti.size()==0){
            return new ResponseEntity<>("fara participanti", HttpStatus.NOT_FOUND);
        }
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        for(Participant p: listaParticipanti){
            participantDtoList.add(participantDtoConverter(p,nrJurat));
        }
        return new ResponseEntity<>(participantDtoList, HttpStatus.OK);
    }

    @PostMapping()
    public void adaugaPunctaj(@RequestBody ObjectNode objectNode){
        Integer idParticipant = Integer.parseInt(objectNode.get("id").asText());
        Integer punctajJurat1 = Integer.parseInt(objectNode.get("numarPuncteJurat1").asText());
        Integer punctajJurat2 = Integer.parseInt(objectNode.get("numarPuncteJurat2").asText());
        Integer punctajJurat3 = Integer.parseInt(objectNode.get("numarPuncteJurat3").asText());
        StatusParticipant status = StatusParticipant.valueOf(objectNode.get("statusParticipant").asText());

        Participant participant = Participant.builder()
                .numarPuncteJurat1(punctajJurat1)
                .numarPuncteJurat2(punctajJurat2)
                .numarPuncteJurat3(punctajJurat3)
                .statusParticipant(status)
                .build();
        participant.setId(idParticipant);

        mainService.adaugaPuncte(participant);

        this.initSendMessage();
       // return new ResponseEntity<>("", HttpStatus.OK);
    }

    private void initSendMessage() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "update";

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    private ParticipantDto participantDtoConverter(Participant participant, NrJurat numarJurat) {
        ParticipantDto participantDto = null;
        switch (numarJurat){
            case JURAT1: {
                participantDto = ParticipantDto.builder()
                        .id(participant.getId())
                        .nume(participant.getNume())
                        .statusParticipant(participant.getStatusParticipant())
                        .numarPuncteJurat(participant.getNumarPuncteJurat1())
                        .build();
                break;
            }
            case JURAT2: {
                participantDto = ParticipantDto.builder()
                    .id(participant.getId())
                    .nume(participant.getNume())
                    .statusParticipant(participant.getStatusParticipant())
                    .numarPuncteJurat(participant.getNumarPuncteJurat2())
                    .build();
                break;
            }

            case JURAT3: {
                participantDto = ParticipantDto.builder()
                        .id(participant.getId())
                        .nume(participant.getNume())
                        .statusParticipant(participant.getStatusParticipant())
                        .numarPuncteJurat(participant.getNumarPuncteJurat3())
                        .build();
                break;
            }
        }
        return participantDto;
    }

}
