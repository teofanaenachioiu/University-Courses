package com.skijummping.skijummping.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.skijummping.skijummping.domain.*;
import com.skijummping.skijummping.repository.CarteRepository;
import com.skijummping.skijummping.repository.JocRepository;
import com.skijummping.skijummping.repository.JucatorRepository;
import com.skijummping.skijummping.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class MainService {
    private static final String EXCHANGE_NAME = "logs";
    JocRepository jocRepository;
    CarteRepository carteRepository;
    JucatorRepository jucatorRepository;
    UtilizatorRepository utilizatorRepository;
    List<String> cartiPosibile = Arrays.asList("6", "7", "8", "9", "J", "Q", "K", "A");
    Map<Integer, String> cartiJucate = new HashMap<>();
    Map<String, Integer> cartiMapate = mapareCarti();
    List <String> cartiDeTimis = new ArrayList<>();

    private Map<String, Integer> mapareCarti() {
        Map<String, Integer> cartiMap = new HashMap<>();
        cartiMap.put("6", 6);
        cartiMap.put("7", 7);
        cartiMap.put("8", 8);
        cartiMap.put("9", 9);
        cartiMap.put("J", 10);
        cartiMap.put("Q", 11);
        cartiMap.put("K", 12);
        cartiMap.put("A", 13);
        return cartiMap;

    }

    @Autowired
    MainService(JocRepository jocRepository, JucatorRepository jucatorRepository,
                CarteRepository carteRepository, UtilizatorRepository utilizatorRepository) {
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
        this.carteRepository = carteRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    private List<String> genereaza4cartiRandom() {
        int numberOfElements = 4;
        List<String> result = new ArrayList<>();
        List<String> givenList = new ArrayList<>(cartiPosibile);
        Random rand = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String randomElement = givenList.get(randomIndex);
            givenList.remove(randomIndex);
            result.add(randomElement);
        }
        return result;
    }

    private List<Carte> genereazaCartiPentruJucator(Jucator jucator) {
        List<Carte> carteList = new ArrayList<>();
        for (String denumire : this.genereaza4cartiRandom()) {
            Carte carte = Carte.builder()
                    .activa(true)
                    .castigata(false)
                    .denumire(denumire)
                    .jucatorMapat(jucator)
                    .build();
            carteList.add(carte);
        }
        return carteList;
    }

    private List<Jucator> utilizatoriConectati() {
        return this.utilizatorRepository.findAll()
                .stream()
                .filter(Utilizator::isConectat)
                .map(x -> Jucator.builder()
                        .castigator(false)
                        .password(x.getPassword())
                        .username(x.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean checkCanPlay() {
        int dimensiune = (int) jocRepository.count();
        Joc ultimulJoc = this.jocRepository.findAll().get(dimensiune - 1);
        return ultimulJoc.getStareJoc().equals(StareJoc.FINISHED);
    }

    public void depundeCarte(Integer idJoc, Integer idJucator, String denumire) {
//        Joc joc = this.jocRepository.findById(idJoc).get();
        Jucator jucator = this.jucatorRepository.findById(idJucator).get();
        for (Carte cartiJucator : jucator.getCarti()) {
            if (!cartiJucator.isCastigata() && denumire.equals(cartiJucator.getDenumire())) {
                carteRepository.updateCarteActiva(cartiJucator.getId(), false);
                Integer nrMutariCurente = jucator.getJocMapat().getMutariTura();
                Integer idJocCurent = jucator.getJocMapat().getId();
                jocRepository.updateNrPasi(nrMutariCurente + 1, idJocCurent);
                cartiJucate.put(jucator.getId(), denumire);
                System.out.println("CARTI JUCATE: ");
                System.out.println(cartiJucate);
                turaCompleta(idJoc);

            }
        }
    }

    public void setCastigatorTura() {
        Integer idCastigator = 0;
        Integer carteCastigatoareMapata = 0;
        for (Integer idJucator : cartiJucate.keySet()) {
            String carteJucataDeJucator = cartiJucate.get(idJucator);
            Integer carteJucataMapata = cartiMapate.get(carteJucataDeJucator);
            if (carteJucataMapata > carteCastigatoareMapata) {
                carteCastigatoareMapata = carteJucataMapata;
                idCastigator = idJucator;
            }
            if (carteJucataMapata.equals(carteCastigatoareMapata)) {
                return;
            }
        }

        Jucator jucatorCastigator = this.jucatorRepository.findById(idCastigator).get();
        cartiJucate.remove(idCastigator);
        for (String carteCastigata : cartiJucate.values()) {
            Carte carteCastigataLaJoc = Carte.builder()
                    .activa(false)
                    .castigata(true)
                    .denumire(carteCastigata)
                    .jucatorMapat(jucatorCastigator)
                    .build();
            carteRepository.save(carteCastigataLaJoc);
        }
    }


    private void turaCompleta(Integer idJoc) {
        Joc jocFound = jocRepository.findById(idJoc).get();

        Integer nrJucatori = jocFound.getJucatori().size();
        Integer nrMutariTura = jocFound.getMutariTura() % nrJucatori;
        System.out.println("MUTARI TURA: "+ jocFound.getMutariTura());
        if (jocFound.getMutariTura().equals(nrJucatori * 4 -1)) {
            this.jocRepository.updateStatus(StareJoc.FINISHED, idJoc);
            cartiJucate.clear();
            initSendMessage("done");
        }
        else if (nrMutariTura.equals(0)){
            setCastigatorTura();
            cartiDeTimis = new ArrayList<>(this.cartiJucate.values());
            initSendMessage("tura");
        }
    }

    public Joc incepeJoc() {
        Joc jocNou = Joc.builder()
                .stareJoc(StareJoc.IN_PROCESS)
                .mutariTura(0)
                .build();
        List<Jucator> jucatorList = this.utilizatoriConectati();
        for (Jucator jucator : jucatorList) {
            jucator.setCarti(this.genereazaCartiPentruJucator(jucator));
            jucator.setJocMapat(jocNou);
        }
        jocNou.setJucatori(jucatorList);
        return this.jocRepository.save(jocNou);
    }

    public List<Jucator> getJucatori(Integer idJoc) {
        Joc joc = jocRepository.findById(idJoc).get();
        return joc.getJucatori();
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

//    public boolean checkEndGame(Integer idJoc) {
//        Joc jocFound = jocRepository.findById(idJoc).get();
//        return jocFound.getStareJoc().equals(StareJoc.FINISHED);
//    }

    public List<Jucator> getCastigatori(Integer idJoc) {
        Joc joc = jocRepository.findById(idJoc).get();
        int maxPuncte = 0;
        List<Jucator> castigatori = new ArrayList<>();
        for (Jucator jucator : joc.getJucatori()) {
            int nrPuncte = 0;
            for (Carte carte : jucator.getCarti()) {
                if (carte.isCastigata()) {
                    nrPuncte += cartiMapate.get(carte.getDenumire());
                }
            }
            if(nrPuncte>maxPuncte){
                castigatori.clear();
                castigatori.add(jucator);
            }
            else if (nrPuncte==maxPuncte){
                castigatori.add(jucator);
            }
        }
        return castigatori;
    }

    public List<String> getCarti(Integer idJoc) {
        return this.cartiDeTimis;
    }
}
