package domain;

/**
 * Clasa Inregistrare
 * idInregistrare - numar intreg >0
 * student - entitate Student
 * tama - entitate Tema
 * dataCurenta - numar intreg: 1..14
 * notaProf - numar intreg: 1..10
 * notaFinala - numar intreg: 1..10 (nota profesorului din care se scad penalizarile)
 */
public class Inregistrare implements HasID<String> {
    private String idInregistrare;
    private Student student;
    private Tema tema;
    private Integer dataCurenta;
    private Integer notaProf;
    private Float notaFinala;

    /**
     * Se calculeaza nota finala cu penalizari
     */
    private void calculeazaNota(){
        Integer dif=dataCurenta-tema.getDeadline();
        if(dif<=2) {
            this.notaFinala= this.notaProf - dif * 2.5f;
        }
        else {
            this.notaFinala=1f;
        }
    }

    /**
     * Getter pentru id inregistrare
     */
    @Override
    public String getID() {
        return idInregistrare;
    }

    /**
     * Setter pentru id inregistrare
     * @param integer - valoare de setat pentru id
     */
    @Override
    public void setID(String integer) {
        this.idInregistrare=idInregistrare;
    }
}
