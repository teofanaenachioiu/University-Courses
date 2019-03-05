package model;

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
}
