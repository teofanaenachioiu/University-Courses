import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartApplication extends Application {
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/loginView.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene mainMenuScene = new Scene(rootLayout, 600, 400);

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);
            loginController.setMainScene(mainMenuScene);

            primaryStage.setScene(mainMenuScene);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Examen");

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}