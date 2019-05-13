package model;

import java.io.Serializable;
import java.util.Objects;

public class Participant implements HasID<Integer>, Serializable {
    private Integer id;
    private String nume;
    private Integer varsta;

    public Participant(Integer id, String nume, Integer varsta) {
        this.nume = nume;
        this.varsta = varsta;
        this.id = id;
    }

    public Participant(String nume, Integer varsta) {
        this.nume = nume;
        this.varsta = varsta;
        this.id = null;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public void setID(Integer integer) {
        this.id = integer;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(getNume(), that.getNume()) &&
                Objects.equals(getVarsta(), that.getVarsta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getNume(), getVarsta());
    }

    @Override
    public String toString() {
        return id + " " + nume + " " + varsta;
    }
}
