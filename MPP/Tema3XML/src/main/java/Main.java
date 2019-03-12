import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.ParticipantRepository;
import repository.ProbaRepository;
import service.ParticipantService;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.traceEntry();

        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:sping-config.xml");

        ProbaRepository repoP=factory.getBean(ProbaRepository.class);
        System.out.println("Dimensiune repo proba: "+repoP.size());
        ParticipantService serviceP=factory.getBean(ParticipantService.class);
        System.out.println("Dimensiune service participant: "+serviceP.size());
        System.out.println("Hello!");

        logger.traceExit();

    }
}
