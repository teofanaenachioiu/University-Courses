package model;

import javafx.util.Pair;

public class Inscriere implements HasID<Pair<Integer,Integer>> {
    private Integer idParticipant;
    private Integer idProba;

    public Inscriere(Integer idParticipant, Integer idProba) {
        this.idParticipant = idParticipant;
        this.idProba = idProba;
    }

    @Override
    public Pair<Integer, Integer> getID() {
        return new Pair<>(idParticipant,idProba);
    }

    @Override
    public void setID(Pair<Integer, Integer> integerIntegerPair) {
        this.idParticipant=integerIntegerPair.getKey();
        this.idProba=integerIntegerPair.getValue();
    }
}
