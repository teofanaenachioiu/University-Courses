package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Nota {

    private Student student;
    private Tema tema;
    private double value;
    private LocalDateTime date;

    public Nota(Student student, Tema tema, double value, LocalDateTime date) {
        this.student = student;
        this.tema = tema;
        this.value = value;
        this.date=date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

//    private static DateTimeFormatter dateFormatter =
//            //DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .toFormatter();

    public String getDateAsString(){return date.format(dateFormatter);}

    public String getDate(){return getDateAsString();}


    @Override
    public String toString() {
        return "Nota{" +
                "student=" + student.toString() +
                ", tema=" + tema.toString() +
                ", value=" + value +
                ", date=" + getDateAsString() +
                '}';
    }
}
