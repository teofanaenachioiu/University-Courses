package view;

import domain.Entity1;
import domain.Entity12;
import domain.Entity2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.Service;

public class Main extends Application {
    Service service=new Service();

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("MainView.fxml"));
        AnchorPane rootLayout=loader.load();

        MainController mainController=loader.getController();

        Scene mainScene=new Scene(rootLayout);
        mainController.setStage(primaryStage);

        FXMLLoader loader1=new FXMLLoader();
        loader1.setLocation(getClass().getResource("Entity1View.fxml"));
        BorderPane borderPane=loader1.load();
        Scene sceneE1=new Scene(borderPane);

        Entity1Controller entity1Controller=loader1.getController();
        entity1Controller.setMainScene(mainScene);
        entity1Controller.setStage(primaryStage);
        entity1Controller.initData(service);

        mainController.setEntity1Scene(sceneE1);
        mainController.setEntity12Scene(sceneE1);

        primaryStage.setTitle("Entities");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
