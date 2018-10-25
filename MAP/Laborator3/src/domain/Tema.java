package domain;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tema implements HasID<Integer>{
    private Integer idTema;
    private String descriere;
    private Integer deadline;
    private Integer dataPredare;

    public Tema(Integer idTema, String descriere, Integer deadline,Integer dataPredare) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.dataPredare = dataPredare;
    }

    private Integer getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

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
        if(deadline>=getLabNumber())
            this.deadline = deadline;
    }

    public Integer getDataPredare() {
        return dataPredare;
    }

    public void setDataPredare(Integer dataPredare) {
        if(this.deadline>=dataPredare)
            this.dataPredare = dataPredare;
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
