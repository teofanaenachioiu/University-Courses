package com.practic.examen.service;

import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.domain.StareJoc;
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
                JucatorRepository jucatorRepository) {
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

    public Joc startJoc(Jucator jucator) {
        List<Jucator> listaJucatori = new ArrayList<>();
        listaJucatori.add(jucator);
        Joc jocNou = Joc.builder()
                .stareJoc(StareJoc.NEW)
                .jucatori(listaJucatori)
                .build();
        jucator.setJocMapat(jocNou);
        return jocRepository.save(jocNou);
    }

    public boolean verificaTrebuieCreatJocNou() {
        int dimensiune = (int) jocRepository.count();
        return (dimensiune == 0 || !jocRepository.findAll().get(dimensiune - 1).getStareJoc().equals(StareJoc.NEW));
    }

    public Jucator getAdversar(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (!jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }

    public Joc setJucatorPentruJoculCurent(Jucator jucator) {
        int dimensiune = (int) jocRepository.count();
        Joc jocCurent = jocRepository.findAll().get(dimensiune - 1);
        jucator.setJocMapat(jocCurent);

        jucatorRepository.save(jucator);

        jocRepository.setStareJoc(jocCurent.getId(), StareJoc.IN_PROGRESS);
        jocCurent = jocRepository.findAll().get(dimensiune - 1);
        initSendMessage("start-" + getAdversar(jocCurent.getId(), jucator.getUsername()).getUsername());
        return jocCurent;
    }

    public Jucator getJucator(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }

    public boolean verificaPozitie(Integer pozitie, Integer idJoc, Integer idJucator) {
        // Joc joc = jocRepository.findById(idJoc).get();
        Jucator jucatorCurent = jucatorRepository.findById(idJucator).get();
        Jucator jucatorAdversar = getAdversar(idJoc, jucatorCurent.getUsername());
        boolean ghicit = false;

        Integer numarIncercari = jucatorCurent.getNrIncercari();
        jucatorRepository.setNumarIncercari(jucatorCurent.getId(), numarIncercari+1);

        if (jucatorAdversar.getPozitie1().equals(pozitie) && !jucatorAdversar.isPozitie1Ghicita()) {
            jucatorRepository.setPozitie1Ghicita(jucatorAdversar.getId(), true);
            jucatorAdversar.setPozitie1Ghicita(true);
            initSendMessage("ghicit");
            ghicit = true;
        }
        if (jucatorAdversar.getPozitie2().equals(pozitie) && !jucatorAdversar.isPozitie2Ghicita()) {
            jucatorRepository.setPozitie2Ghicita(jucatorAdversar.getId(), true);
            jucatorAdversar.setPozitie2Ghicita(true);
            initSendMessage("ghicit");
            ghicit = true;
        }
        if (jucatorAdversar.isPozitie1Ghicita() && jucatorAdversar.isPozitie2Ghicita()){
            jucatorRepository.setCastigator (jucatorCurent.getId(), true);
            jocRepository.setStareJoc(idJoc, StareJoc.FINISHED);
            initSendMessage("done/"+jucatorCurent.getUsername());
        }
        jucatorRepository.setMuta(jucatorCurent.getId(), false);
        jucatorRepository.setMuta(jucatorAdversar.getId(), true);
        initSendMessage("tura"+jucatorAdversar.getUsername()+"/"+pozitie);
        return ghicit;
    }

    public List<Joc> listaJocuriUtilizator(String username){
        List<Joc> jocList = new ArrayList<>();
        for(Joc joc: jocRepository.findAll()){
            for (Jucator jucator: joc.getJucatori()){
                if(jucator.getUsername().equals(username)){
                    jocList.add(joc);
                }
            }
        }
        return jocList;
    }

}
