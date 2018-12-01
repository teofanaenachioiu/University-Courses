package domain;

import javafx.util.Pair;

import java.util.Objects;

/**
 * Clasa Nota
 * student - entitate Student
 * tama - entitate Tema
 * dataCurenta - numar intreg: 1..14
 * notaProf - numar real: 1..10
 * notaFinala - numar real: 1..10 (nota profesorului din care se scad penalizarile)
 */

public class Nota implements HasID<Pair<String, String>> {
    private String studentID;
    private String temaID;
    private String dataCurenta;
    private String notaProf;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTemaID() {
        return temaID;
    }

    public void setTemaID(String temaID) {
        this.temaID = temaID;
    }

    public String getDataCurenta() {
        return dataCurenta;
    }

    public void setDataCurenta(String dataCurenta) {
        this.dataCurenta = dataCurenta;
    }

    public Float getNotaProf() {
        return Float.parseFloat(notaProf);
    }

    public void setNotaProf(String notaProf) {
        this.notaProf = notaProf;
    }


    public Nota(String studentID, String temaID, String dataCurenta, String notaProf) {
        this.studentID = studentID;
        this.temaID = temaID;
        this.dataCurenta = dataCurenta;
        this.notaProf = notaProf;
        //calculeazaNota();
    }


    @Override
    public Pair<String, String> getID() {
        return new Pair(this.studentID,this.temaID);
    }

    @Override
    public void setID(Pair<String, String> stringStringPair) {
        this.studentID=stringStringPair.getKey();
        this.temaID=stringStringPair.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nota)) return false;
        Nota nota = (Nota) o;
        return
                Objects.equals(getStudentID(), nota.getStudentID()) &&
                Objects.equals(getTemaID(), nota.getTemaID()) &&
                Objects.equals(getDataCurenta(), nota.getDataCurenta()) &&
                Objects.equals(getNotaProf(), nota.getNotaProf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentID(), getTemaID(), getDataCurenta(), getNotaProf());
    }

    @Override
    public String toString() {
        return studentID + "/" + temaID + "/" + dataCurenta + "/" + notaProf;
    }
}
