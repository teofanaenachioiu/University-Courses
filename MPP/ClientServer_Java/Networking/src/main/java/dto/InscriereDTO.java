package dto;

import model.Proba;

import java.io.Serializable;
import java.util.Arrays;

public class InscriereDTO implements Serializable {
    private String nume;
    private Integer varsta;
    private Proba[] probe;
    private String usernameOperator;

    public InscriereDTO(String nume, Integer varsta, Proba[] probe, String usernameOperator) {
        this.nume = nume;
        this.varsta = varsta;
        this.probe = probe;
        this.usernameOperator = usernameOperator;
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

    public Proba[] getProbe() {
        return probe;
    }

    public void setProbe(Proba[] probe) {
        this.probe = probe;
    }

    public String getUsernameOperator() {
        return usernameOperator;
    }

    public void setUsernameOperator(String usernameOperator) {
        this.usernameOperator = usernameOperator;
    }

    @Override
    public String toString() {
        return "InscriereDTO{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", probe=" + Arrays.toString(probe) +
                ", usernameOperator='" + usernameOperator + '\'' +
                '}';
    }
}
