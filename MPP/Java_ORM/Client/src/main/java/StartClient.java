import GUI.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServer;

public class StartClient extends Application {
    public void start(Stage primaryStage) {
        System.out.println("In start");
        try {
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IServer server = (IServer) factory.getBean("concursService");
            System.out.println("Obtained a reference to remote chat server");
            System.out.println("Hello!");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewLogin.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene mainMenuScene = new Scene(rootLayout, 600, 400);

            ControllerLogin controllerLogin = loader.getController();
            controllerLogin.init(server, mainMenuScene);

            controllerLogin.setPrimaryStage(primaryStage);

            primaryStage.setScene(mainMenuScene);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Concurs");

            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Chat Initialization  exception:" + e);
            e.printStackTrace();
        }
    }
}
