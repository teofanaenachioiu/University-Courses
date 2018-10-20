package domain;

public class Student implements HasID<Integer> {
    private Integer idStudent;
    private String nume;
    private Integer grupa;
    private String email;
    private String indrumatorLab;

    public Student(Integer idStudent, String nume, Integer grupa, String email, String indrumatorLab) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.indrumatorLab = indrumatorLab;
    }

    public String getNume() {
        return this.nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getGrupa() {
        return this.grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndrumatorLab() {
        return this.indrumatorLab;
    }

    public void setIndrumatorLab(String indrumatorLab) {
        this.indrumatorLab = indrumatorLab;
    }

    @Override
    public Integer getID() {
        return this.idStudent;
    }

    @Override
    public void setID(Integer integer) {
        this.idStudent=integer;
    }
}
