package domain;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Clasa Tema
 */
public class Tema implements HasID<String>{
    private String idTema;
    private String descriere;
    private String deadline;
    private String dataPredare;

    /**
     * @param idTema - numar intreg >0 (id temei)
     * @param descriere - String (descrierea temei)
     * @param deadline - String 1..14 (saptamana limita de predare a temei)
     * @param dataPredare - String 1..14 (saptamana in care a fost primita tema)
     */
    public Tema(String idTema, String descriere, String deadline,String dataPredare) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.dataPredare = dataPredare;
    }

    /**
     * Se determina saptamana curenta
     * @return saptamna curenta
     */
    private Integer getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    /**
     * Se determina numarul laboratorului (tinand cont de vacanta de iarna)
     * @return dif (nurmarul laboratorului)
     */
    private Integer getLabNumber(){
        Integer saptCurenta=getCurrentWeek();
        Integer dif=saptCurenta-39;
        if(dif<1||dif>16) return null;
        if(saptCurenta.equals(13) || saptCurenta.equals(14))
            return 12;
        if(saptCurenta.equals(15)) return dif-1;
        if(saptCurenta.equals(16)) return dif-2;
        return dif;
    }

    /**
     * Getter pentru descrierea temei
     * @return descriere
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * Setter pentru descrierea temei
     * @param descriere (descrierea de setat)
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    /**
     * Getter pentru deadline-ul laboratorului
     * @return deadline
     */
    public Integer getDeadline() {
        return Integer.parseInt(deadline);
    }

    /**
     * Setter pentru deadline
     * @param deadline (deadline-ul de setat)
     */
    public void setDeadline(String deadline) {
        if(Integer.parseInt(deadline)>=getLabNumber())
            this.deadline = deadline;
    }

    /**
     * Getter pentru data de predare
     * @return dataPredare
     */
    public Integer getDataPredare() {
        return Integer.parseInt(dataPredare);
    }

    /**
     * Setter pentru saptamana de predare a laboratorului
     * @param dataPredare (saptamana de predare a temei de laborator)
     */
    public void setDataPredare(String dataPredare) {
        if(Integer.parseInt(this.deadline)>=Integer.parseInt(dataPredare))
            this.dataPredare = dataPredare;
    }

    /**
     * Getter pentru id tema
     * @return idTema
     */
    @Override
    public String getID() {
        return this.idTema;
    }

    /**
     * Setter pentru id-ul temei
     * @param integer (id-ul de setat pentru tema)
     */
    @Override
    public void setID(String integer) {
        this.idTema=integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tema)) return false;
        Tema tema = (Tema) o;
        return Objects.equals(idTema, tema.idTema) &&
                Objects.equals(getDescriere(), tema.getDescriere()) &&
                Objects.equals(getDeadline(), tema.getDeadline()) &&
                Objects.equals(getDataPredare(), tema.getDataPredare());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTema, getDescriere(), getDeadline(), getDataPredare());
    }

    @Override
    public String toString() {
        return idTema + '/' +
                descriere + '/' +
                deadline + '/' +
                dataPredare;
    }
}
