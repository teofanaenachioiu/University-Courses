import javafx.util.Pair;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.traceEntry();

        ApplicationContext context=new AnnotationConfigApplicationContext(RepositoryConfig.class);
        ParticipantRepository repoP=context.getBean(ParticipantRepository.class);
        System.out.println("Dim:" +repoP.size());
        System.out.println("Hello!");

        logger.traceExit();

    }
}
