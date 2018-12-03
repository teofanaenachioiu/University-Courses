package noteReports;

import model.Nota;
import model.Student;
import model.Tema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepoNote {

    private static List<Student> getStudents()
    {
        return Arrays.asList(
                new Student(1,"s1"),
                new Student(2,"s2"),
                new Student(3,"s3"),
                new Student(4,"s4")
        );

    }

    private static List<Tema> getTeme()
    {
        return Arrays.asList(
                new Tema("t1","desc1"),
                new Tema("t2","desc2"),
                new Tema("t3","desc3"),
                new Tema("t4","desc4")
                );

    }

    private static List<Nota> getNote(List<Student> stud, List<Tema> teme)
    {
        DateTimeFormatter formatter= Nota.getDateFormatter();
        LocalDateTime date1=LocalDateTime.parse("2018-12-24",formatter);
        LocalDateTime date2=LocalDateTime.parse("2018-11-22",formatter);
        LocalDateTime date3=LocalDateTime.parse("2018-10-23",formatter);
        LocalDateTime date4=LocalDateTime.parse("2018-10-14",formatter);
        LocalDateTime date5=LocalDateTime.parse("2018-12-24",formatter);
        LocalDateTime date6=LocalDateTime.parse("2018-10-14",formatter);
        LocalDateTime date7=LocalDateTime.parse("2018-12-24",formatter);
        return Arrays.asList(
                new Nota(stud.get(0),teme.get(0),10d, date1),
                new Nota(stud.get(0),teme.get(0),9d, date2),
                new Nota(stud.get(1),teme.get(1),10d , date3),
                new Nota(stud.get(1),teme.get(2),10d, date4),
                new Nota(stud.get(2),teme.get(1),7d,date5),
                new Nota(stud.get(2),teme.get(3),9d,date6),
                new Nota(stud.get(1),teme.get(3),10d,date7)
        );
    }

    public static List<Nota> findAll() {
        return getNote(getStudents(),getTeme());
    }


}
