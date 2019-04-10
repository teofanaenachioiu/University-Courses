package repository;

import javafx.util.Pair;
import model.Inscriere;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class InscrieriRepositoryTest {
    @Test
    public void test(){
        IRepositoryInscriere repo;
        Inscriere inscriere=new Inscriere(1,10,"maria_avram");

        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\Persistence\\src\\test\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        repo=new InscriereRepository(prop);


        //size
        assertEquals(0,repo.size());

        //save
        repo.save(inscriere);
        assertEquals(1,repo.size());

        //findone
        assertEquals(inscriere,repo.findOne(new Pair<>(1,10)));

        //update
        repo.update(new Pair<>(1,10),new Inscriere(1,10,"ioana_avram"));

        List<Inscriere> lista=
                StreamSupport.stream(repo.findAll().spliterator(),false )
                        .collect(Collectors.toList());

        inscriere.setUsernameOperator("ioana_avram");
        assertEquals(inscriere,lista.get(lista.size()-1));

        repo.delete(inscriere.getID());

        //nr Probe/Participant
        repo.save(new Inscriere(1,10,"maria_avram"));
        repo.save(new Inscriere(1,11,"maria_avram"));
//        lista= StreamSupport.stream(((InscrieriRepository) repo).findProbeDupaParticipant(1).spliterator(),false)
//                        .collect(Collectors.toList());
//        assertEquals(2,lista.size());

        //max 2 probe

        //cautaParticipantiDupaCategorie
        long dim=StreamSupport
                .stream(repo.cautaParticipantiDupaCategorie("CATEGORIE_6_8").spliterator(),false)
                .count();
        assertEquals(1,dim);
        assertEquals(1,repo.nrParticipantiCategorie("CATEGORIE_6_8"));

        //cautaParticipantiDupaProba
        dim=StreamSupport
                .stream(repo.cautaParticipantiDupaProba("Pictura").spliterator(),false)
                .count();
        assertEquals(2,dim);
        //assertEquals(2,repo.nrParticipantiProba("Pictura"));

        //cautaParticipantDupaProbaCategorie
        dim=StreamSupport
                .stream(repo.cautaParticipantDupaProbaCategorie("Pictura","CATEGORIE_6_8").spliterator(),false)
                .count();
        assertEquals(1,dim);


        try{
            repo.save(new Inscriere(1,12,"maria_avram"));
            assertEquals(2,repo.size());
        }
        catch (RepositoryException e){
            assertEquals("Participantul e deja inscris la doua probe",e.getMessage());
            assertEquals(2,repo.size());
            repo.delete(new Pair<>(1,10));
            repo.delete(new Pair<>(1,11));
        }




    }

}