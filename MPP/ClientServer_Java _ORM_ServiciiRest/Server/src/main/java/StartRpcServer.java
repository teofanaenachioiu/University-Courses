
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class StartRpcServer {

    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");


    }
}
