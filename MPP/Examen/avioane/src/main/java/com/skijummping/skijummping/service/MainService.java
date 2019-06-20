package com.skijummping.skijummping.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.skijummping.skijummping.domain.Joc;
import com.skijummping.skijummping.domain.Jucator;
import com.skijummping.skijummping.domain.StareJoc;
import com.skijummping.skijummping.repository.JocRepository;
import com.skijummping.skijummping.repository.JucatorRepository;
import com.skijummping.skijummping.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class MainService {
    private static final String EXCHANGE_NAME = "logs";
    UtilizatorRepository utilizatorRepository;
    JocRepository jocRepository;
    JucatorRepository jucatorRepository;

    @Autowired
    MainService(UtilizatorRepository utilizatorRepository, JocRepository jocRepository,
                JucatorRepository jucatorRepository){
        this.utilizatorRepository = utilizatorRepository;
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
    }

    public Jucator getJucatorByUsername(Integer idJoc, String username){
        for (Jucator j: jocRepository.findById(idJoc).get().getJucatori()){
            if(j.getUsername().equals(username))
                return j;
        }
        return null;
    }

    public Jucator getAdversar(Integer idJoc, String username){
        for (Jucator j: jocRepository.findById(idJoc).get().getJucatori()){
            if(!j.getUsername().equals(username))
                return j;
        }
        return null;
    }

    public List<Joc> listaJocuriJucator(String username){
        List<Joc> list=new ArrayList<>();
        for(Joc joc: jocRepository.findAll()){
            for(Jucator jucator: joc.getJucatori()){
                if(jucator.getUsername().equals(username))
                    list.add(joc);
            }
        }
        return list;
    }

    //Jucator: username, pozitie avion
    // nu are: muta, castigator, joc mapat
    //Joc: stare joc, lista jucatori
    public Joc startJoc(Jucator jucator){
        int nrJocuri = (int) jocRepository.count();

        if(nrJocuri == 0 || !jocRepository.findAll().get(nrJocuri - 1).getStareJoc().equals(StareJoc.NEW)){
            Joc jocNou = Joc.builder()
                    .stareJoc(StareJoc.NEW)
                    .build();

            List<Jucator> jucatorList= new ArrayList<>();
            jucator.setMuta(true);
            jucator.setCastigator(false);
            jucator.setJocMapat(jocNou);
            jucatorList.add(jucator);

            jocNou.setJucatori(jucatorList);
            return jocRepository.save(jocNou);
        }
        Joc ultimulJoc = jocRepository.findAll().get(nrJocuri - 1);
        if(ultimulJoc.getStareJoc().equals(StareJoc.NEW)){
            jucator.setMuta(false);
            jucator.setCastigator(false);
            jucator.setJocMapat(ultimulJoc);
            jucatorRepository.save(jucator);
            jocRepository.setStareJoc(ultimulJoc.getId(), StareJoc.IN_PROCESS);
            initSendMessage("conectat");
            return jocRepository.findAll().get(nrJocuri - 1);
        }
        return null;
    }

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

    public void setCastigator (Integer idJoc, Integer idJucator){
        jucatorRepository.setJucatorCastigator(idJucator, true);
        jocRepository.setStareJoc(idJoc, StareJoc.FINISHED);
        initSendMessage(idJucator.toString());
    }

    public Jucator getJucatorCareMuta(Integer idJoc){
        Joc joc = jocRepository.findById(idJoc).get();
        Jucator jucator1 = joc.getJucatori().get(0);
        Jucator jucator2 = joc.getJucatori().get(1);
        if(jucator1.isMuta()){
            jucatorRepository.setJucatorMuta(jucator1.getId(), false);
            jucatorRepository.setJucatorMuta(jucator2.getId(), true);
            jucator2.setMuta(true);
            initSendMessage("tura");
            return jucator2;
        }
        else {
            jucatorRepository.setJucatorMuta(jucator1.getId(), true);
            jucatorRepository.setJucatorMuta(jucator2.getId(), false);
            jucator1.setMuta(true);
            initSendMessage("tura");
            return jucator1;
        }
    }
}
