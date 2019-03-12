package tasks;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tasks.service.TaskService;
import tasks.view.SortingTasksFXML;

/**
 * Created by grigo on 3/9/17.
 */
public class SortingMainJavaConfig extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SortingTasks Example");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("view/tasks.fxml"));
        Pane myPane = (Pane) loader.load();
        SortingTasksFXML ctrl=loader.getController();

        ctrl.setTasksService(getTasksService());
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ctrl.close();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static TaskService getTasksService(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SortingTasksConfig.class);
        TaskService service=context.getBean(TaskService.class);
        return service;
    }

}
