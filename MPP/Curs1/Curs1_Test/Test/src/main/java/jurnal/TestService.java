package jurnal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by grigo on 2/27/17.
 */
public class TestService {
    private Logger logger = LogManager.getLogger(TestService.class.getName());

    private String[] messages = new String[] {
            "Hello!",
            "Goodbye!",
            "Hi!"
    };
    private Random rand = new Random(1);

    public void setMessages(String[] messages) {
        logger.traceEntry("Messages {}",(Object)messages);
        this.messages = messages;
        logger.traceExit();
    }

    public String[] getMessages() {
        logger.traceEntry();
        return logger.traceExit(messages);
    }

    public String retrieveMessage() {
        logger.traceEntry();

        String testMsg = getMessage(getKey());

        return logger.traceExit(testMsg);
    }

    public void exampleException() {
        logger.traceEntry();
        try {
            String msg = messages[messages.length];
            logger.error("An exception should have been thrown");
        } catch (Exception ex) {
            logger.catching(ex);
        }
        logger.traceExit();
    }

    public String getMessage(int key) {
        logger.entry(key);

        String value = messages[key];

        return logger.traceExit(value);
    }

    private int getKey() {
        logger.traceEntry();
        int key = rand.nextInt(messages.length);
        return logger.traceExit(key);
    }
}
