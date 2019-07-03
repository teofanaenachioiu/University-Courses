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
import java.util.Random;
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

    private String genereazaTraseu() {
        String traseu = "_________";
        Random rand = new Random();
        int rand_int1 = rand.nextInt(3);
        return traseu.substring(0, rand_int1) + 'x' + traseu.substring(rand_int1 + 1);
    }

    public Joc startJoc(String username) {
        Jucator jucator = Jucator.builder()
                .muta(true)
                .username(username)
                .numereGenerate("")
                .pozitii("")
                .litera("a")
                .build();
        List<Jucator> listaJucatori = new ArrayList<>();
        listaJucatori.add(jucator);
        Joc jocNou = Joc.builder()
                .stareJoc(StareJoc.NEW)
                .jucatori(listaJucatori)
                .traseu(genereazaTraseu())
                .nrMutari(0)
                .build();

        jucator.setJocMapat(jocNou);

        return jocRepository.save(jocNou);
    }

    public boolean verificaTrebuieCreatJocNou() {
        int dimensiune = (int) jocRepository.count();
        return (dimensiune == 0 || !jocRepository.findAll().get(dimensiune - 1).getStareJoc().equals(StareJoc.NEW));
    }

    public Joc setJucatorNouLaJoculCurent(String username) {
        int dimensiune = (int) jocRepository.count();
        Joc jocCurent = jocRepository.findAll().get(dimensiune - 1);
        Jucator jucator = Jucator.builder()
                .muta(false)
                .username(username)
                .numereGenerate("")
                .pozitii("")
                .jocMapat(jocCurent)
                .litera("b")
                .build();

        jucatorRepository.save(jucator);
        jocRepository.setStareJoc(jocCurent.getId(), StareJoc.IN_PROGRESS);
        jocCurent = jocRepository.findAll().get(dimensiune - 1);
        initSendMessage("start-" + getAdversar(jocCurent.getId(), username).getUsername());
        return jocCurent;
    }

    public Jucator getJucator(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }

    public Jucator getAdversar(Integer idJoc, String username) {
        for (Jucator jucator : jocRepository.findById(idJoc).get().getJucatori()) {
            if (!jucator.getUsername().equals(username))
                return jucator;
        }
        return null;
    }

    private String getLiteraAdversar(Jucator jucator) {
        String literaAdversar = "a";
        if (jucator.getLitera().equals("a")) {
            literaAdversar = "b";
        }
        return literaAdversar;
    }

    private int undeArTrebuiSaMAPun(Jucator jucator, Integer numarulGenerat) {
        String pozitiileMele = jucator.getPozitii();
        if (pozitiileMele.length() == 0)
            return numarulGenerat - 1;
        String ultimaPozitie = pozitiileMele.substring(pozitiileMele.length() - 1);
        int pozUltim = Integer.parseInt(ultimaPozitie);
        return pozUltim + numarulGenerat - 1;
    }

    private int ultimaPozitie(Jucator jucator) {
        String pozitiileMele = jucator.getPozitii();
        if (pozitiileMele.length() == 0) return -1;
        String ultimaPozitie = pozitiileMele.substring(pozitiileMele.length() - 1);
        return Integer.parseInt(ultimaPozitie);
    }

    //returneaza pozitia jucatorului pe traseu
    private String refacereTraseu(Joc joc, Jucator jucator, int pozitiaUndeTrebuiePus) {
        String nouaPoz = "";
        String traseu = joc.getTraseu();
        String literaAdversar =getLiteraAdversar(jucator);
        if (traseu.charAt(pozitiaUndeTrebuiePus) == 'x') {

            nouaPoz = getPozitieLibera(traseu, pozitiaUndeTrebuiePus-1, literaAdversar);
            int pozitieDeMutat = Integer.parseInt(nouaPoz);
            String traseuNou = traseu.substring(0, pozitieDeMutat)+jucator.getLitera()+traseu.substring(pozitieDeMutat+1);
            jocRepository.setTraseu(joc.getId(), traseuNou);
            return nouaPoz;
        }

        char literaA = literaAdversar.charAt(0);

        if (traseu.charAt(pozitiaUndeTrebuiePus) == literaA) {
            nouaPoz = "0";
            int ultimaPoz = ultimaPozitie(jucator);
            if(ultimaPoz!=-1){
                String traseuNou = traseu.substring(0, ultimaPoz)+"_"+traseu.substring(ultimaPoz+1);
                jocRepository.setTraseu(joc.getId(), traseuNou);
            }
            return nouaPoz;
        }

        if(jucator.getPozitii().length()==0){
            nouaPoz = String.valueOf(pozitiaUndeTrebuiePus+1);
            String traseuNou = traseu.substring(0, pozitiaUndeTrebuiePus)+jucator.getLitera()+traseu.substring(pozitiaUndeTrebuiePus+1);
            jocRepository.setTraseu(joc.getId(), traseuNou);
            return nouaPoz;
        }

        int ultimaPoz = ultimaPozitie(jucator);
        String traseuNou = traseu.substring(0, ultimaPoz)+"_"+traseu.substring(ultimaPoz+1);
        traseuNou= traseuNou.substring(0, pozitiaUndeTrebuiePus)+jucator.getLitera()+traseuNou.substring(pozitiaUndeTrebuiePus+1);
        jocRepository.setTraseu(joc.getId(), traseuNou);
        nouaPoz = String.valueOf(pozitiaUndeTrebuiePus+1);
        return nouaPoz;
    }

    public void mutareJucator(Jucator jucator) {
        jucatorRepository.setNumere(jucator.getId(), jucator.getNumereGenerate());

        Jucator jucatorFound = jucatorRepository.findById(jucator.getId()).get();
        Joc joc = jucatorFound.getJocMapat();

        Integer lungi = jucatorFound.getNumereGenerate().length();
        int numarulGenerat = Integer.parseInt(jucatorFound.getNumereGenerate().substring(lungi - 1));
        int undeTrebuieSaMaPun = undeArTrebuiSaMAPun(jucatorFound, numarulGenerat);
        String nouaPoz = refacereTraseu(joc, jucatorFound, undeTrebuieSaMaPun);

        jucatorRepository.setPozitie(jucatorFound.getId(), jucatorFound.getPozitii() + nouaPoz);

        joc.setNrMutari(joc.getNrMutari() + 1);
        jocRepository.setNumarMutari(joc.getId(), joc.getNrMutari());

        String mesaj = String.valueOf(numarulGenerat) + "numar" + getAdversar(joc.getId(),
                jucatorFound.getUsername()).getUsername();
        initSendMessage(mesaj);

        if (joc.getNrMutari().equals(6)) {
            jocRepository.setStareJoc(joc.getId(), StareJoc.FINISHED);
            initSendMessage("done");
        }
    }

    private String getPozitieLibera(String traseu, int lastPoz, String literaAdversar) {
        if (lastPoz == -1) {
            return "0";
        }
        for (int i = lastPoz; i >= 0; i--) {
            String poz = String.valueOf(traseu.charAt(i));
            if (!poz.equals(literaAdversar))
                return String.valueOf(i);
        }
        return "0";
    }


    public String getTraseuJoc(Integer idJoc) {
        return jocRepository.findById(idJoc).get().getTraseu();
    }
}
