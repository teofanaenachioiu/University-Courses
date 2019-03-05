package domain;

import java.util.Objects;

public class Student {
    private Integer nrMatricol;
    private String nume;
    private String facultate;
    private Integer an;

    public Student(Integer nrMatricol, String nume, String facultate, Integer an) {
        this.nrMatricol = nrMatricol;
        this.nume = nume;
        this.facultate = facultate;
        this.an = an;
    }

    public Integer getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(Integer nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getNrMatricol().equals(student.getNrMatricol()) &&
                getAn().equals(student.getAn()) &&
                Objects.equals(getNume(), student.getNume()) &&
                Objects.equals(getFacultate(), student.getFacultate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNrMatricol(), getNume(), getFacultate(), getAn());
    }
}
