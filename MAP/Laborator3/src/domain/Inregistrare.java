package domain;

public class Inregistrare implements HasID<Integer> {
    private Integer idInregistrare;
    private Student student;
    private Tema tema;
    private Integer dataCurenta;
    private Integer notaProf;
    private Float notaFinala;



    private void calculeazaNota(){
        Integer dif=dataCurenta-tema.getDeadline();
        if(dif<=2) {
            this.notaFinala= this.notaProf - dif * 2.5f;
        }
        else {
            this.notaFinala=1f;
        }
    }

    @Override
    public Integer getID() {
        return idInregistrare;
    }

    @Override
    public void setID(Integer integer) {
        this.idInregistrare=idInregistrare;
    }
}
