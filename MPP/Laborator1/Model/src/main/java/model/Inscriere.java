package model;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Objects;

public class Inscriere implements HasID<Pair<Integer,Integer>> {
    private Integer idParticipant;
    private Integer idProba;
    private LocalDate data;
    private String usernameOperator;

    public Inscriere(Integer idParticipant, Integer idProba, String usernameOperator) {
        this.idParticipant = idParticipant;
        this.idProba = idProba;
        this.usernameOperator=usernameOperator;
        this.data=LocalDate.now();
    }
    public Inscriere(Integer idParticipant, Integer idProba, LocalDate data, String usernameOperator) {
        this.idParticipant = idParticipant;
        this.idProba = idProba;
        this.usernameOperator=usernameOperator;
        this.data=data;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getUsernameOperator() {
        return usernameOperator;
    }

    public void setUsernameOperator(String usernameOperator) {
        this.usernameOperator = usernameOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscriere)) return false;
        Inscriere inscriere = (Inscriere) o;
        return Objects.equals(idParticipant, inscriere.idParticipant) &&
                Objects.equals(idProba, inscriere.idProba) &&
                Objects.equals(getData(), inscriere.getData()) &&
                Objects.equals(getUsernameOperator(), inscriere.getUsernameOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipant, idProba, getData(), getUsernameOperator());
    }

    @Override
    public String toString() {
        return "Inscriere{" +
                "idParticipant=" + idParticipant +
                ", idProba=" + idProba +
                ", data=" + data +
                ", usernameOperator='" + usernameOperator + '\'' +
                '}';
    }
}
