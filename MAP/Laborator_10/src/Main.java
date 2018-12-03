import view.GradeController;
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
        init(primaryStage);
    }

    private void init(Stage primaryStage) throws IOException {


        //main menu


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenuView.fxml"));
        AnchorPane rootLayout = loader.load();

        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout,720,600);

        controller.setPrimaryStage(primaryStage);


       // Student view
        StudentController studentController=new StudentController(service);
        service.addObserver(studentController);
        StudentView studentView=new StudentView(studentController);
        studentController.setView(studentView);
        Scene scene=new Scene(studentView.getView(),720,600);
        studentController.setPrimaryStage(primaryStage);
        studentController.setMainScene(mainMenuScene);

        // Grade view

        FXMLLoader gradeLoader = new FXMLLoader();
        gradeLoader.setLocation(getClass().getResource("views/GradeView.fxml"));
        BorderPane gradeLayout = gradeLoader.load();
        GradeController gradeController = gradeLoader.getController();
        gradeController.setService(service);
        Scene gradeScene=new Scene(gradeLayout,720,600);
        gradeController.setPrimaryStage(primaryStage);
        gradeController.setMainScene(mainMenuScene);

        controller.setStudentScene(scene);
        controller.setGradeStage(gradeScene);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

}
