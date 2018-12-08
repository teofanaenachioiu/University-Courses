package ExercitiuCurs5;

public class StudentHelper  {
    public static int compareByName(Student a, Student b){
        return a.getNume().compareTo(b.getNume());
    }
}
