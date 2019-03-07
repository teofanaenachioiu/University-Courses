import javafx.util.Pair;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.traceEntry();
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }



//        IRepository<Integer,Participant> repoParticipant=new ParticipantRepository(prop);
//        repoParticipant.save(new Participant("Maria",8));
//        repoParticipant.save(new Participant("Ioana",6));
//        repoParticipant.save(new Participant("Vlad",14));
//        repoParticipant.save(new Participant("Andrei",10));
//        repoParticipant.save(new Participant("Alexandra",9));

//        IRepository<Integer,Proba> repository=new ProbaRepository(prop);
//        repository.save(new Proba("Pictura", Categorie.CATEGORIE_6_8));
//        repository.save(new Proba("Pictura", Categorie.CATEGORIE_9_11));
//        repository.save(new Proba("Pictura", Categorie.CATEGORIE_12_15));
//        repository.save(new Proba("Inot", Categorie.CATEGORIE_12_15));
//        repository.save(new Proba("Tir cu arcul", Categorie.CATEGORIE_12_15));

//        IRepository<Pair<Integer,Integer>, Inscriere> repo=new InscrieriRepository(prop);
//        repo.save(new Inscriere(1,10,"maria_avram"));
//        repo.save(new Inscriere(3,10,"maria_avram"));
//        repo.save(new Inscriere(4,13,"maria_avram"));
//        repo.save(new Inscriere(4,14,"maria_avram"));

//        for(Inscriere i:repo.findAll()){
//            System.out.println(i);
//        }

        System.out.println("Hello!");
        logger.traceExit();
    }
}
