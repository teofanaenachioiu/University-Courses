import java.util.Objects;

public class Student {
    private int nrMatricol;
    private String nume;
    private String facultate;
    private int an;

    public Student(int nrMatricol, String nume, String facultate, int an) {
        this.nrMatricol = nrMatricol;
        this.nume = nume;
        this.facultate = facultate;
        this.an = an;
    }

    public int getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(int nrMatricol) {
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

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getNrMatricol() == student.getNrMatricol() &&
                getAn() == student.getAn() &&
                Objects.equals(getNume(), student.getNume()) &&
                Objects.equals(getFacultate(), student.getFacultate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNrMatricol(), getNume(), getFacultate(), getAn());
    }
}
