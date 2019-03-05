import domain.Student;
import repository.IRepository;
import repository.StudentRepository;

import java.io.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Tema2\\src\\main\\resources\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer,Student> repo=new StudentRepository(prop);
        System.out.println("Inainte de adaugare");
        for(Student s:repo.findAll()){
            System.out.println(s.getNume());
        }
        repo.save(new Student(100,"Alex","FMI",2));
        System.out.println("Dupa adaugare");
        for(Student s:repo.findAll()){
            System.out.println(s.getNume());
        }
        repo.delete(100);
        repo.delete(6);
    }
}
