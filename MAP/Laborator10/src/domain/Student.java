package domain;

import java.util.Objects;

/**
 * Clasa Student
 */
public class Student implements HasID<String> {
    private String idStudent;
    private String nume;
    private String grupa;
    private String email;
    private String indrumatorLab;

    /**
     * @param idStudent - numar intreg >0 (id-ul studentului)
     * @param nume - String (numele studentului)
     * @param grupa - numar intreg (grupa din care face parte studentul)
     * @param email - string (email-ul studentului)
     * @param indrumatorLab - String (numele indrumatorului de la laborator)
     */
    public Student(String idStudent, String nume, String grupa, String email, String indrumatorLab) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.indrumatorLab = indrumatorLab;
    }

    /**
     * Getter pentru numele studentului
     * @return nume
     */
    public String getNume() {
        return this.nume;
    }

    /**
     * Setter pentru numele studentului
     * @param nume - numele de stetat
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Getter pentru grupa studentului
     * @return grupa
     */
    public String getGrupa() {
        return this.grupa;
    }

    /**
     * Setter pentru grupa studentului
     * @param grupa - grupa de setat
     */
    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    /**
     * Getter pentru emailul studentului
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter pentru emailul studentului
     * @param email - email de setat
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pentru numele indrumatorului de la laborator
     * @return indrumatorLab
     */
    public String getIndrumatorLab() {
        return this.indrumatorLab;
    }

    /**
     * Setter pentru numele indrumatorului de la laborator
     * @param indrumatorLab - numele de setat
     */
    public void setIndrumatorLab(String indrumatorLab) {
        this.indrumatorLab = indrumatorLab;
    }

    @Override
    public String getID() {
        return this.idStudent;
    }

    @Override
    public void setID(String integer) {
        this.idStudent=integer;
    }

    @Override
    public String toString() {
        return this.idStudent +
                "/" + this.nume +
                "/" + this.grupa +
                "/" + this.email +
                "/" + this.indrumatorLab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(idStudent, student.idStudent) &&
                Objects.equals(getNume(), student.getNume()) &&
                Objects.equals(getGrupa(), student.getGrupa()) &&
                Objects.equals(getEmail(), student.getEmail()) &&
                Objects.equals(getIndrumatorLab(), student.getIndrumatorLab());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent, getNume(), getGrupa(), getEmail(), getIndrumatorLab());
    }
}
