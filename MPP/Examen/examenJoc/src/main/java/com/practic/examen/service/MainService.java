package com.practic.examen.service;

import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.domain.StareJoc;
import com.practic.examen.domain.Utilizator;
import com.practic.examen.repository.JocRepository;
import com.practic.examen.repository.JucatorRepository;
import com.practic.examen.repository.UtilizatorRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class MainService {
    private static final String EXCHANGE_NAME = "logss";

    private UtilizatorRepository utilizatorRepository;
    private JocRepository jocRepository;
    private JucatorRepository jucatorRepository;

    @Autowired
    MainService(UtilizatorRepository utilizatorRepository, JocRepository jocRepository,
                JucatorRepository jucatorRepository){
        this.utilizatorRepository = utilizatorRepository;
        this.jucatorRepository = jucatorRepository;
        this.jocRepository = jocRepository;
    }

    /*************************************************************
     *                          Coada                            *
     *************************************************************/
    private void initSendMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    /*************************************************************
     *                Implementare servicii                      *
     *************************************************************/

    public Joc startJoc() {
        List<Jucator> listaJucatori = listaUtilizatoriConectati();
        listaJucatori.get(0).setMuta(true);
        Joc jocNou = Joc.builder()
                .stareJoc(StareJoc.IN_PROGRESS)
                .jucatori(listaJucatori)
                //adauga restul campurilor !!!!!!!!!!!!!!!!
                .build();
        for (Jucator jucator : listaJucatori) {
            jucator.setJocMapat(jocNou);
        }
        Joc joc = jocRepository.save(jocNou);
        initSendMessage("start");
        return joc;
    }

    public boolean canPlay() {
        int dimensiune = (int) jocRepository.count();
        return (dimensiune == 0 || jocRepository.findAll().get(dimensiune - 1).getStareJoc().equals(StareJoc.FINISHED));
    }

    public Joc curentGame() {
        int dimensiune = (int) jocRepository.count();
        return jocRepository.findAll().get(dimensiune - 1);
    }

    private List<Jucator> listaUtilizatoriConectati() {
        List<Jucator> jucatoriList = new ArrayList<>();
        for (Utilizator utilizator : utilizatorRepository.findAll()) {
            if (utilizator.isConectat()) {
                Jucator jucator = Jucator.builder()
                        .username(utilizator.getUsername())
                        // adauga restul campurilor !!!!!!!!!!!!!!!!!!!
                        .build();
                jucatoriList.add(jucator);
            }
        }
        return jucatoriList;
    }

    public Jucator getJucator(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }
}
