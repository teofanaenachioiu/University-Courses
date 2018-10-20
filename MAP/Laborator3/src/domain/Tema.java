package domain;


public class Tema implements HasID<Integer>{
    private Integer idTema;
    private String descriere;
    private Integer deadline;
    private Integer dataPredare;
    private Float nota;
    private Float notaCopie;

    public Tema(Integer idTema, String descriere, Integer deadline,Integer dataPredare, Float nota) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.dataPredare = dataPredare;
        this.nota = nota;
        this.notaCopie=nota;
        calculeazaNota();
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
        calculeazaNota();
    }

    public Integer getDataPredare() {
        return dataPredare;
    }

    public void setDataPredare(Integer dataPredare) {
        this.dataPredare = dataPredare;
        calculeazaNota();
    }

    public Float getNota() {
        return this.nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;

    }

    private void calculeazaNota(){
        Integer dif=getDataPredare()-getDeadline();
        if(dif<=2) {
            this.nota= this.notaCopie - dif * 2.5f;
        }
        else {
            this.nota=1f;
        }
    }

    @Override
    public Integer getID() {
        return this.idTema;
    }

    @Override
    public void setID(Integer integer) {
        this.idTema=integer;
    }
}
