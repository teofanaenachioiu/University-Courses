package model;

public class Proba implements HasID<Integer> {
    private Integer id;
    private String descriere;
    private Enum<Categorie> catg;

    public Proba(String descriere, Enum<Categorie> catg) {
        this.descriere = descriere;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Enum<Categorie> getCatg() {
        return catg;
    }

    public void setCatg(Enum<Categorie> catg) {
        this.catg = catg;
    }
}
