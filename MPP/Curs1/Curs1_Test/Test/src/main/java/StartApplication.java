import domain.MessageTask;
import domain.MessageTaskValidator;
import domain.ValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.MessageTaskFileRepository;
import repository.MessageTaskInMemoryRepository;
import service.TaskServiceManager;
import view.MessageTaskController;
import view.MessageTaskView;

import java.time.LocalDateTime;

public class StartApplication extends Application
{

    //MessageTaskFileRepository repo=new MessageTaskFileRepository(new MessageTaskValidator(),"data/Messages.txt");
    MessageTaskInMemoryRepository repo=new MessageTaskInMemoryRepository(new MessageTaskValidator());
    TaskServiceManager ser=new TaskServiceManager(repo);

    @Override
  public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(initView(), 750, 600));
        primaryStage.show();
    }

    private Parent initView()
    {
        MessageTaskController ctrl=new MessageTaskController(ser);
        ser.addObserver(ctrl);
        MessageTaskView view=new MessageTaskView(ctrl);
        ctrl.setView(view);
        return view.getView();
    }

    public static void main(String[] args) {

        launch(args);
    }
}