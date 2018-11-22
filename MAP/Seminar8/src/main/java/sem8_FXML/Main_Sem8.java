package sem8_FXML;
import common.domain.MessageTask;
import common.domain.validator.MessageTaskValidator;
import common.repository.CrudRepository;
import common.repository.InFileMessageTaskRepository;
import common.service.MessageTaskService;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sem8_FXML.controller.*;

import java.io.IOException;


public class Main_Sem8 extends Application {
    private CrudRepository<String, MessageTask> messageTaskRepository;
    MessageTaskService messageTaskService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        messageTaskRepository = new InFileMessageTaskRepository
                ("./data/Messages", new MessageTaskValidator());
        messageTaskService=new MessageTaskService(messageTaskRepository);

        init2(primaryStage);

    }
    private void init2(Stage primaryStage) throws IOException {

        //main menu

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainMenuView.fxml"));
        BorderPane rootLayout = loader.load();
        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout);
        primaryStage.setScene(mainMenuScene);
        controller.setPrimaryStage(primaryStage);


        //2.MessageTask view
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/messageTaskView224.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        MessageTask224 messageTaskController = messageLoader.getController();
        messageTaskController.setMessageTaskService(messageTaskService);
        controller.setCenterMessageLayout(messageTaskLayout);
        rootLayout.setCenter(messageTaskLayout);


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
    private void init1(Stage primaryStage) throws Exception{
        //main menu

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainMenuView.fxml"));
        BorderPane rootLayout = loader.load();
        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout);
        primaryStage.setScene(mainMenuScene);
        controller.setPrimaryStage(primaryStage);


        //2.MessageTask view
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/messageTaskView.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        MessageTaskController messageTaskController = messageLoader.getController();
        messageTaskController.setMessageTaskService(messageTaskService);
        controller.setCenterMessageLayout(messageTaskLayout);
        rootLayout.setCenter(messageTaskLayout);


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

//    // set Scence -
//    private void init2(Stage primaryStage) throws Exception{
//
//        //main menu
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/mainMenuView_V2.fxml"));
//        AnchorPane menuLayout = loader.load();
//        MainMenuController_V2 menuController = loader.getController();
//        menuController.setPrimaryStage(primaryStage);
//
//
//        //2.MessageTask view
//        FXMLLoader messageLoader = new FXMLLoader();
//        messageLoader.setLocation(getClass().getResource("views/messageTaskView.fxml"));
//        AnchorPane messageTaskLayout = messageLoader.load();
//        MessageTaskController messageTaskController = messageLoader.getController();
//        messageTaskController.setMessageTaskService(messageTaskService);
//
//        BorderPane messagesBorderPaneLayout=new BorderPane();
//        messagesBorderPaneLayout.setTop(menuLayout);
//        messagesBorderPaneLayout.setCenter(messageTaskLayout);
//        Scene messageScene=new Scene(messagesBorderPaneLayout);
//        menuController.setMessageScene(messageScene);
//
//
//        //2.Grade view
//        FXMLLoader gradeLoader = new FXMLLoader();
//        gradeLoader.setLocation(getClass().getResource("views/gradesView.fxml"));
//        AnchorPane gradeLayout = gradeLoader.load();
//        GradeController gradeController = gradeLoader.getController();
//        //gradeController.setGradeTaskService(messageGradeService);
//
//        BorderPane gradeBorderPaneLayout=new BorderPane();
//        gradeBorderPaneLayout.setTop(menuLayout);
//        gradeBorderPaneLayout.setCenter(gradeLayout);
//        Scene gradeScene=new Scene(gradeBorderPaneLayout);
//        menuController.setGradeScene(gradeScene);
//
//
//        primaryStage.setScene(gradeScene);
//        primaryStage.show();
//    }
}
