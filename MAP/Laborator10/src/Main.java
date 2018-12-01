import view.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.Service;
import view.StudentController;
import view.StudentView;

import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private Service service=new Service("./src/data/Studenti.xml","./src/data/Teme.xml","./src/data/Catalog.xml");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File("./src/resources/teacher.png").toURI().toString()));
        primaryStage.setTitle("MAP Class Book Manager");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File("./src/resources/teacher.png").toURI().toString()));

        init(primaryStage);
    }

    private void init(Stage primaryStage) throws IOException {

        //main menu

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenuView.fxml"));
        AnchorPane rootLayout = loader.load();

        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout);

        controller.setPrimaryStage(primaryStage);


       // Student view
        StudentController ctrl=new StudentController(service);
        service.addObserver(ctrl);
        StudentView view=new StudentView(ctrl);
        ctrl.setView(view);
        Scene scene=new Scene(view.getView(),720,600);
        controller.setStudentScene(scene);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

    }

}
