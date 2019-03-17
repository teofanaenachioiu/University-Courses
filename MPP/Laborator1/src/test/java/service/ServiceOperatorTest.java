package service;

import model.Proba;
import org.junit.Test;
import repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ServiceOperatorTest {

    @Test
    public void inscriereParticipant() {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\test\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepositoryParticipant repoParticipant=new ParticipantRepository(prop);
        IRepositoryProba repoProba=new ProbaRepository(prop);
        IRepositoryInscriere repoInscriere=new InscriereRepository(prop);
        ServiceOperator service = new ServiceOperator(repoParticipant,repoProba,repoInscriere);

        List<Proba> listaProbe= (List<Proba>) repoProba.findAll();
        try{
            service.inscriereParticipant("Teofana",10,listaProbe,"maria");
        }
        catch (RepositoryException e){
            assertEquals(e.getMessage(),"Participantul nu se poate inscrie la mai mult de 2 probe");
        }

        listaProbe=listaProbe.subList(0,2);

        service.inscriereParticipant("Teofana",10,listaProbe,"maria");
        service.stergeToateInregistrarile();
    }
}