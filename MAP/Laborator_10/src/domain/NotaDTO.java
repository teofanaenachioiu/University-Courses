package domain;

import java.util.ArrayList;
import java.util.List;

public class NotaDTO  {
    private String idStudent;
    private String idTema;
    private String numeStudent;
    private Float nota;

    public NotaDTO(Nota notaLab) {
        this.idStudent=notaLab.getStudentID();
        this.numeStudent="faraNume";
        this.idTema=notaLab.getTemaID();
        this.nota=notaLab.getNotaProf();
    }


    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }
}
