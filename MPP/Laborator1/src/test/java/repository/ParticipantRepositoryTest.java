package repository;

import model.Participant;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;


public class ParticipantRepositoryTest {
    IRepository<Integer, Participant> repo;
    public ParticipantRepositoryTest() {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

       repo=new ParticipantRepository(prop);
    }

    @Test
    public void size() {
        assertEquals(3,repo.size());
    }

    @Test
    public void save() {

    }
}