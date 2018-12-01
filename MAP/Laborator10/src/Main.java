import controller.MainMenuController;
import domain.Student;
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

    private void init(Stage primaryStage) throws IOException {

        //main menu

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenuView.fxml"));
        BorderPane rootLayout = loader.load();
        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout);
        primaryStage.setScene(mainMenuScene);
        controller.setPrimaryStage(primaryStage);


        //2.Student view
        FXMLLoader studentLoader = new FXMLLoader();
        studentLoader.setLocation(getClass().getResource("/view/StudentView.fxml"));
        AnchorPane studentLayout = studentLoader.load();
        StudentController studentController = studentLoader.getController();
        studentController.setService(service);
        ////controller.setCenterMessageLayout(messageTaskLayout);
        rootLayout.setCenter(studentLayout);


        //2.Grade view
        FXMLLoader gradeLoader = new FXMLLoader();
        gradeLoader.setLocation(getClass().getResource("/views/gradesView.fxml"));
        AnchorPane gradeLayout = gradeLoader.load();
        GradeController gradeController = gradeLoader.getController();
        //gradeController.setGradeTaskService(messageGradeService);
        controller.setCenterGradeLayout(gradeLayout);


        primaryStage.show();
//        primaryStage.setWidth(500);
//        primaryStage.setHeight(500);

    }

}
