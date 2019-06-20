package com.practic.examen.service;

import com.practic.examen.domain.*;
import com.practic.examen.repository.CuvantRepository;
import com.practic.examen.repository.JocRepository;
import com.practic.examen.repository.JucatorRepository;
import com.practic.examen.repository.UtilizatorRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class MainService {
    private static final String EXCHANGE_NAME = "logs";

    private UtilizatorRepository utilizatorRepository;
    private JocRepository jocRepository;
    private JucatorRepository jucatorRepository;
    private CuvantRepository cuvantRepository;

    @Autowired
    MainService(UtilizatorRepository utilizatorRepository, JocRepository jocRepository,
                JucatorRepository jucatorRepository, CuvantRepository cuvantRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
        this.cuvantRepository = cuvantRepository;
    }

    public static String shuffleString(String string)
    {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled = shuffled + letter;
        }
        return shuffled;
    }

    private List<String> genereaza3Cuvinte() {
        List<String> cuvinte = new ArrayList<>();
        while (cuvinte.size()<3){
            int rnd = new Random().nextInt((int) cuvantRepository.count());
            Cuvant cuvant = cuvantRepository.findAll().get(rnd);
            if(!cuvinte.contains(cuvant.getDenumire())){
                cuvinte.add(cuvant.getDenumire());
            }
        }
        return cuvinte;
    }

    public boolean canPlay(){
        int numarJocuri = (int) jocRepository.count();
        return numarJocuri == 0 || jocRepository.findAll().get(numarJocuri - 1).getStareJoc().equals(StareJoc.FINISHED);
    }

    private List<Jucator> getUtilizatoriConectati() {
        List<Jucator> jucatori = new ArrayList<>();
        for(Utilizator utilizator: utilizatorRepository.findAll()){
            if(utilizator.isConectat()){
                jucatori.add(Jucator.builder()
                .username(utilizator.getUsername())
                .numarPuncte(0)
                .castigator(false)
                .raspuns("")
                .build());
            }
        }
        return jucatori;
    }

    public Joc startJoc() {
        List<Jucator> listaJucatori = this.getUtilizatoriConectati();
        List<String> cuvinte = genereaza3Cuvinte();
        Joc jocNou = Joc.builder()
                .numarMutari(0)
                .cuvant1(cuvinte.get(0))
                .cuvant2(cuvinte.get(1))
                .cuvant3(cuvinte.get(2))
                .stareJoc(StareJoc.IN_PROGRESS)
                .jucatori(listaJucatori)
                .build();
        for(Jucator jucator: listaJucatori){
            jucator.setJocMapat(jocNou);
        }
        jocRepository.save(jocNou);
        initSendMessage("startJoc");
        return jocNou;
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

    public List<Jucator> getJucatori(Integer idJoc) {
        Joc joc = jocRepository.findById(idJoc).get();
        return joc.getJucatori();
    }

    public Joc lastGame() {
        return jocRepository.findAll().get((int) (jocRepository.count()-1));
    }

    public Joc trimiteRaspuns(Jucator jucator, Integer idJoc) {
        Joc joc = jocRepository.findById(idJoc).get();
        for(Jucator jucator1: joc.getJucatori()){
            if(jucator.getUsername().equals(jucator1.getUsername())){
                jocRepository.incrementeazaPasi(idJoc, joc.getNumarMutari()+1);
                jucatorRepository.setNumarPuncte(jucator1.getId(), jucator.getNumarPuncte());
                System.out.println("incrementeaza pasi");
            }
        }
        joc.setNumarMutari(joc.getNumarMutari() + 1);
        turaCompleta(joc);
        return jocRepository.findById(idJoc).get();
    }

    private void turaCompleta(Joc joc){
        if(joc.getNumarMutari().equals(joc.getJucatori().size()*3)){
            jocRepository.setStareJoc(joc.getId(),StareJoc.FINISHED);
            setCastigator(joc);
            initSendMessage("finish");
        }
        if(joc.getNumarMutari().equals(joc.getJucatori().size())){
            initSendMessage("tura2");
        }
        if(joc.getNumarMutari().equals(joc.getJucatori().size()*2)){
            initSendMessage("tura3");
        }
    }

    private void setCastigator(Joc joc) {
        int maxScor = 0;
        List<Jucator> jucatoriCastigatori =new ArrayList<>();
        for(Jucator jucator: joc.getJucatori()){
            if(jucator.getNumarPuncte()>maxScor){
                maxScor = jucator.getNumarPuncte();
                jucatoriCastigatori.clear();
                jucatoriCastigatori.add(jucator);
            }
            else if(jucator.getNumarPuncte().equals(maxScor)){
                jucatoriCastigatori.add(jucator);
            }
        }
        for(Jucator jucator: jucatoriCastigatori){
            jucatorRepository.setCastigator(jucator.getId(), true);
        }
    }

    public List<Joc> getJocuri(String username) {
        List<Joc> jocuri = new ArrayList<>();
        for(Joc joc : jocRepository.findAll()){
            for (Jucator jucator: joc.getJucatori())
                if(jucator.getUsername().equals(username))
                    jocuri.add(joc);
        }
        return  jocuri;
    }

    public Jucator getCastigator(Joc joc) {
        Jucator castigator = null;
        Integer maxScor =-1;
        for(Jucator jucator: joc.getJucatori()) {
            if (jucator.getNumarPuncte() > maxScor) {
                maxScor = jucator.getNumarPuncte();
                castigator = jucator;
            }
        }
        return castigator;
    }
}
