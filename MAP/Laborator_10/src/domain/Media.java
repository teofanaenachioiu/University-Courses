package domain;

public class Media {
    String idStudent;
    String numeStudent;
    String grupa;
    Float media;

    public Media(String idStudent, String numeStudent, String grupa, Float media) {
        this.idStudent = idStudent;
        this.numeStudent = numeStudent;
        this.grupa = grupa;
        this.media = media;
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

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Float getMedia() {
        return media;
    }

    public void setMedia(Float media) {
        this.media = media;
    }
}
