import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.Service;
import view.StudentController;
import view.StudentView;

import java.io.File;


public class Main extends Application {

    private Service service=new Service("./src/data/Studenti.xml","./src/data/Teme.xml","./src/data/Catalog.xml");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StudentController ctrl=new StudentController(service);
        service.addObserver(ctrl);
        StudentView view=new StudentView(ctrl);
        ctrl.setView(view);

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File("./src/resources/teacher.png").toURI().toString()));
        primaryStage.setTitle("Student Management System");
        primaryStage.setScene(new Scene(view.getView(), 720, 600));
        primaryStage.show();
    }
}
