import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {
    private static Logger logger = Logger.getLogger(FileLogger.class.getName());

    static
    {
        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(String string) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(string);
        }
    }
}