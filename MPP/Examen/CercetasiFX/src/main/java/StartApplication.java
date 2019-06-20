import controller.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartApplication extends Application {
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewLogin.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene mainMenuScene = new Scene(rootLayout, 600, 400);

            ControllerLogin controllerLogin = loader.getController();
            controllerLogin.setPrimaryStage(primaryStage);
            controllerLogin.setMainScene(mainMenuScene);

            primaryStage.setScene(mainMenuScene);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Concurs");

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}