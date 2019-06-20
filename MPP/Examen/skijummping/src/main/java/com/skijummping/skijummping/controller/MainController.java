package com.skijummping.skijummping.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.skijummping.skijummping.domain.Participant;
import com.skijummping.skijummping.domain.StatusParticipant;
import com.skijummping.skijummping.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/concurs")
public class MainController {
    private static final String EXCHANGE_NAME = "logs";

    MainService mainService;
    @Autowired
    MainController(MainService mainService){
        this.mainService=mainService;
    }

    @GetMapping()
    public ResponseEntity<List<Participant>> getParticipati(){
        return new ResponseEntity<>(mainService.getAllParticipanti(), HttpStatus.OK);
    }

    @GetMapping(value = "/{status}")
    public ResponseEntity<List<Participant>> getParticipatiByStatus(@PathVariable String status){
        StatusParticipant statusParticipant = StatusParticipant.valueOf(status);
        return new ResponseEntity<>(mainService.getAllParticipantiStatus(statusParticipant), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> adaugaPunctaj(@RequestBody ObjectNode objectNode){

        Integer idParticipant = Integer.parseInt(objectNode.get("id").asText());
        Integer punctajPartial = Integer.parseInt(objectNode.get("numarPunctePartial").asText());
        boolean areLungime = Boolean.parseBoolean(objectNode.get("arePunctePentruLungime").asText());
        boolean areStil = Boolean.parseBoolean(objectNode.get("arePunctePentruStil").asText());
        boolean areAterizare = Boolean.parseBoolean(objectNode.get("arePunctePentruAterizare").asText());
        StatusParticipant status = StatusParticipant.valueOf(objectNode.get("statusParticipant").asText());
        Participant participant = Participant.builder()
                .numarPunctePartial(punctajPartial)
                .arePunctePentruAterizare(areAterizare)
                .arePunctePentruLungime(areLungime)
                .arePunctePentruStil(areStil)
                .statusParticipant(status)
                .build();
        participant.setId(idParticipant);
        mainService.adaugaPuncte(participant);
        this.initSendMessage();
        return new ResponseEntity<>("", HttpStatus.OK);
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
}
