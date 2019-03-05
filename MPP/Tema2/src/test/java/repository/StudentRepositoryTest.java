package repository;

import domain.Student;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class StudentRepositoryTest {

    @Test
    public void test() {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Tema2\\src\\main\\resources\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer, Student> repo=new StudentRepository(prop);
        Student student=new Student(6,"Alex","FSEGA",1);

        repo.save(student);

        assertEquals(4,repo.size());

        assertEquals(student,repo.findOne(6));


        repo.update(6,new Student(6,"Alex","FMI",1));

        Student updated=repo.findOne(6);
        repo.delete(6);
        assertEquals("FMI",updated.getFacultate());

        assertEquals(3,repo.size());
    }


}