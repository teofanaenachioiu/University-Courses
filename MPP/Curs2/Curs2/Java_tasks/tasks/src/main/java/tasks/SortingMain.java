package tasks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tasks.controller.TasksController;
import tasks.repository.SortingTaskRepository;
import tasks.repository.SortingTaskValidator;
import tasks.service.TaskService;
import tasks.view.SortingTasksView;

/**
 * Created by grigo on 11/14/16.
 */
public class SortingMain extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SortingTasks Application");
        BorderPane pane=getView();
        Scene scene = new Scene(pane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static BorderPane getView(){
        SortingTaskRepository repo=new SortingTaskRepository(new SortingTaskValidator());

        TaskService service=new TaskService(repo);
        TasksController contr=new TasksController(service);
        SortingTasksView view=new SortingTasksView(contr);
        return view.getView();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
