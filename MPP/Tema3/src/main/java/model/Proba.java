package model;

import java.util.Objects;

public class Proba implements HasID<Integer> {
    private Integer id;
    private String denumire;
    private Enum<Categorie> catg;

    public Proba(Integer id, String denumire, Enum<Categorie> catg) {
        this.denumire = denumire;
        this.catg = catg;
        this.id=id;
    }

    public Proba(String denumire, Enum<Categorie> catg) {
        this.denumire = denumire;
        this.catg = catg;
        this.id=null;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer integer) {
        this.id=integer;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Enum<Categorie> getCatg() {
        return catg;
    }

    public void setCatg(Enum<Categorie> catg) {
        this.catg = catg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proba)) return false;
        Proba proba = (Proba) o;
        return Objects.equals(id, proba.id) &&
                Objects.equals(getDenumire(), proba.getDenumire()) &&
                Objects.equals(getCatg(), proba.getCatg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getDenumire(), getCatg());
    }

    @Override
    public String toString() {
        return "Proba{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", catg=" + catg +
                '}';
    }
}
