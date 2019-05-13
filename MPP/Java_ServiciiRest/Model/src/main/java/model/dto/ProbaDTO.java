package model.dto;

import java.io.Serializable;

public class ProbaDTO implements Serializable {
    private Integer id;
    private String denumire;
    private String categorie;
    private Integer nrParticipanti;

    public ProbaDTO(Integer id, String denumire, String categorie, Integer nrParticipanti) {
        this.id = id;
        this.denumire = denumire;
        this.categorie = categorie;
        this.nrParticipanti = nrParticipanti;
    }

    @Override
    public String toString() {
        return "ProbaDTO{" +
                "denumire='" + denumire + '\'' +
                ", categorie='" + categorie + '\'' +
                ", nrParticipanti=" + nrParticipanti +
                '}';
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(Integer nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
