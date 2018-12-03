package model;

public class NotaDTO {
    private String studentName;
    private String temaId;
    private double nota;

    public NotaDTO(String studentName, String temaDesc, double nota) {
        this.studentName = studentName;
        this.temaId = temaDesc;
        this.nota = nota;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTemaId() {
        return temaId;
    }

    public void setTemaId(String temaId) {
        this.temaId = temaId;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
