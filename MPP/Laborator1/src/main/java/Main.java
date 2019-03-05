import javafx.util.Pair;
import model.Inscriere;
import model.Participant;
import model.Proba;
import model.User;
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

//        IRepository<Pair<Integer,Integer>, Inscriere> repo=new InscrieriRepository(prop);
//        IRepository<String, User> repo=new UserRepository(prop);
//        System.out.println(repo.size());
        System.out.println("Hello!");
        logger.traceExit();
    }
}
