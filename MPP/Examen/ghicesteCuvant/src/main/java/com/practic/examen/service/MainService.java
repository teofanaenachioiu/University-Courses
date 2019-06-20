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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class MainService {
    private static final String QUEUE_NAME = "logs";

    private UtilizatorRepository utilizatorRepository;
    private JocRepository jocRepository;
    private JucatorRepository jucatorRepository;

    @Autowired
    MainService(UtilizatorRepository utilizatorRepository, JocRepository jocRepository,
                JucatorRepository jucatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
    }

    private List<Jucator> listaUtilizatoriConectati() {
        List<Jucator> jucatoriList = new ArrayList<>();
        for (Utilizator utilizator : utilizatorRepository.findAll()) {
            if (utilizator.isConectat()) {
                Jucator jucator = Jucator.builder()
                        .username(utilizator.getUsername())
                        .numarPuncte(0)
                        .castigator(false)
                        .litereIncercate("")
                        .muta(false)
                        .litereGhiciteDinCuvant("")
                        .build();
                jucatoriList.add(jucator);
            }
        }
        return jucatoriList;
    }

    public boolean canPlay() {
        int dimensiune = (int) jocRepository.count();
        return (dimensiune == 0 || jocRepository.findAll().get(dimensiune - 1).getStareJoc().equals(StareJoc.FINISHED));
    }

    public Joc startJoc() {
        List<Jucator> listaJucatori = listaUtilizatoriConectati();
        listaJucatori.get(0).setMuta(true);
        Joc jocNou = Joc.builder()
                .stareJoc(StareJoc.IN_PROGRESS)
                .numarLitereGhicite(0)
                .jucatori(listaJucatori)
                .build();
        for (Jucator jucator : listaJucatori) {
            jucator.setJocMapat(jocNou);
        }
        Joc joc = jocRepository.save(jocNou);
        initSendMessage("start");
        return joc;
    }

    public Integer schimbaTura(Integer idJoc) {
        Joc joc = jocRepository.findById(idJoc).get();
        List<Jucator> jucators = joc.getJucatori();
        Jucator jucatorTura = null;
        for (int i = 0; i < jucators.size(); i++) {
            Jucator jucatorCurent = jucators.get(i);
            if (jucatorCurent.isMuta() && i < jucators.size() - 1) {
                jucatorTura = jucators.get(i + 1);
                jucatorRepository.setMuta(jucatorTura.getId(), true);
                jucatorRepository.setMuta(jucatorCurent.getId(), false);
                initSendMessage("tura-" + jucatorTura.getUsername());
            } else if (jucatorCurent.isMuta() && i == jucators.size() - 1) {
                jucatorTura = jucators.get(0);
                jucatorRepository.setMuta(jucatorTura.getId(), true);
                jucatorRepository.setMuta(jucatorCurent.getId(), false);
                initSendMessage("tura-" + jucatorTura.getUsername());
            }
        }
        return jucatorTura.getId();
    }

    public void setCuvantJucator(Integer idJucator, String cuvantPropus) {
        jucatorRepository.setCuvantPropus(idJucator, cuvantPropus);
        initSendMessage("cuvantNou");
    }

    public List<Jucator> getJucatori(Integer idJoc, String username) {
        return jocRepository.findById(idJoc).get().getJucatori()
                .stream()
                .filter(x -> !x.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public Jucator getJucator(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }

    /*************************************************************
     *                          Coada                            *
     *************************************************************/

    private void initSendMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(QUEUE_NAME, "fanout");

            channel.basicPublish(QUEUE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    public Joc curentGame() {
        int dimensiune = (int) jocRepository.count();
        return jocRepository.findAll().get(dimensiune - 1);
    }

    public void verificaLitera(Integer idAdversar, Integer idJucator, String literaPropusa) {
        Jucator jucatorAdversar = jucatorRepository.findById(idAdversar).get();

        Jucator jucatorCurent = jucatorRepository.findById(idJucator).get();
        System.out.println(jucatorCurent.getUsername() + " a verificat "+ literaPropusa);
        jucatorRepository.setLitere(jucatorCurent.getId(), jucatorCurent.getLitereIncercate() + literaPropusa);

        if (jucatorAdversar.getCuvantPropus().contains(literaPropusa)) {
            int occurance = StringUtils.countOccurrencesOf(jucatorAdversar.getCuvantPropus(), literaPropusa);
            jucatorRepository.setLitereGhicite(jucatorAdversar.getId(),
                    jucatorAdversar.getLitereGhiciteDinCuvant() + literaPropusa);
            Integer nrPuncte = jucatorCurent.getNumarPuncte();
            jucatorRepository.setNumarPuncte(jucatorCurent.getId(), nrPuncte + 1);
            Joc joc = jucatorCurent.getJocMapat();
            jocRepository.setNumarLitereGhicite(joc.getId(), joc.getNumarLitereGhicite() + occurance);
            joc.setNumarLitereGhicite(joc.getNumarLitereGhicite()+occurance);
            initSendMessage("corect");
            checkDone(joc);
            schimbaTura(joc.getId());
        }
    }

    private void checkDone(Joc joc) {
        int numarJucatori = joc.getJucatori().size();
        if (joc.getNumarLitereGhicite().equals(numarJucatori * 3)) {
            jocRepository.setStareJoc(joc.getId(), StareJoc.FINISHED);
            initSendMessage("done");
        }
    }
}
