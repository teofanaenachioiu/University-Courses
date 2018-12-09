package domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotaDTO2 {
    String idStudent;
    String numeStudent;
    List note;

    public NotaDTO2(String idStudent, String numeStudent) {
        this.idStudent = idStudent;
        this.numeStudent = numeStudent;
        this.note= (List<Float>) Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f);
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public List getNote() {
        return note;
    }

    public void setNote(List note) {
        this.note = note;
    }

    public NotaDTO2 setNotaLab(String idLab, Float nota) {
        this.note.set(Integer.valueOf(idLab)-1,nota);
        return this;
    }
}
