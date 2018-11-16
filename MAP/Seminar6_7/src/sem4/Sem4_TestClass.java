package tasks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sem4.domain.MessageTask;
import sem4.domain.validator.MessageTaskValidator;
import sem4.repository.CrudRepository;
import sem4.repository.InFileMessageTaskRepository;
import sem4.service.MessageTaskService;
import sem4.view.MessageTaskController;
import sem4.view.MessageTaskView;


import java.time.LocalDateTime;

public class Sem4_TestClass extends Application {

    CrudRepository<String, MessageTask> repo = new InFileMessageTaskRepository
            ("./data/messages", new MessageTaskValidator());
    MessageTaskService service=new MessageTaskService(repo);

    @Override
    public void start(Stage primaryStage) throws Exception{
        MessageTaskController ctrl=new MessageTaskController(service);
        service.addObserver(ctrl);
        MessageTaskView view=new MessageTaskView(ctrl);
        ctrl.setView(view);

        primaryStage.setTitle("MessageTask CRUD Operations");
        primaryStage.setScene(new Scene(view.getView(), 720, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


//package sem4;
//
//import sem4.domain.Book;
//import sem4.domain.MessageTask;
//import sem4.domain.validator.BookValidator;
//import sem4.domain.validator.MessageTaskValidator;
//import sem4.repository.*;
//import sem4.service.MessageTaskService;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//public class Sem4_TestClass {
//    public static void main(String[] args) {
//
//
//        CrudRepository rep = new XMLMessageTaskRepository(new MessageTaskValidator(), "./data/messages.xml");
//        Iterable<MessageTask> lst = rep.findAll();
//        lst.forEach(m -> System.out.println(m.toString()));
//
//
////        MessageTask msgtest = new MessageTask("9999", "feedback lab 2", "bine", "teacher", "student", LocalDateTime.now());
////        rep.save(msgtest);
//
//    }
//}
