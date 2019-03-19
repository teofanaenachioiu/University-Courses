package repository;

import model.Participant;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;


public class ParticipantRepositoryTest {
    IRepository<Integer, Participant> repo;
    Participant participant=new Participant("Teodora",11);
    Participant lastParticipant;

    public ParticipantRepositoryTest() {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\test\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

       repo=new ParticipantRepository(prop);

    }

    @Test
    public void test() {
        //size
        assertEquals(5,repo.size());

        //save
        repo.save(participant);
        assertEquals(6,repo.size());
        List<Participant> lista=
                StreamSupport.stream(repo.findAll().spliterator(),false )
                        .collect(Collectors.toList());
        lastParticipant=lista.get(lista.size()-1);
        participant.setID(lastParticipant.getID());

        //findone
        assertEquals(participant,repo.findOne(lastParticipant.getID()));

        //update
        repo.update(lastParticipant.getID(),new Participant("Teodora",6));

        lista=
                StreamSupport.stream(repo.findAll().spliterator(),false )
                        .collect(Collectors.toList());
        lastParticipant=lista.get(lista.size()-1);

        participant.setVarsta(6);
        assertEquals(participant,lastParticipant);

        repo.delete(lastParticipant.getID());
    }

}