package tasks;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tasks.repository.SortingTaskJdbcRepository;
import tasks.service.TaskService;
import tasks.utils.ObservableTaskRunner;
import tasks.utils.TaskStack;
import tasks.view.SortingTasksFXML;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by grigo on 11/22/16.
 */
public class SortingFXMain extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FXML TableView Example");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("view/tasks.fxml"));
        Pane myPane = (Pane) loader.load();
       SortingTasksFXML ctrl=loader.getController();

        ctrl.setTasksService(getTasksService());
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
              //  System.out.println("Stage is closing");
                ctrl.close();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static TaskService  getTasksService(){
          Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return null;
        }
        SortingTaskJdbcRepository repo=new SortingTaskJdbcRepository(serverProps);//Repository(new SortingTaskValidator());
        ObservableTaskRunner runner=new ObservableTaskRunner(new TaskStack());
        TaskService service=new TaskService(repo,runner);
        return service;
    }
}
