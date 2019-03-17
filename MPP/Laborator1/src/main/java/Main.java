import GUI.ControllerLogin;
import GUI.operator.ControllerOperator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;
import service.ServiceAdmin;
import service.ServiceOperator;
import utils.PasswordStorage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.traceEntry();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Concurs");
        init(primaryStage);
        logger.traceExit();
    }

    private void init(Stage primaryStage) throws IOException, PasswordStorage.CannotPerformOperationException {

        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepositoryParticipant repoParticipant=new ParticipantRepository(prop);
        IRepositoryProba repoProba=new ProbaRepository(prop);
        IRepositoryInscriere repoInscriere=new InscriereRepository(prop);
        ServiceOperator service = new ServiceOperator(repoParticipant,repoProba,repoInscriere);
        ServiceAdmin serviceAdmin=new ServiceAdmin(new UserRepository(prop));

//        serviceAdmin.createUser();
//        serviceAdmin.createUser("maria_avram","parola");
        System.out.println("Hello!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewLogin.fxml"));
        AnchorPane rootLayout = loader.load();
        Scene mainMenuScene = new Scene(rootLayout,600,400);

        ControllerLogin controllerLogin=loader.getController();
        controllerLogin.init(service,serviceAdmin,mainMenuScene);

        controllerLogin.setPrimaryStage(primaryStage);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

    }
}
