package view;

import domain.Nota;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController {
    private Service service;
    private ObservableList<Nota> model;

    private Stage primaryStage;
    private Scene mainScene;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    public GradeController(Service service) {
        setService(service);
        List<Nota> list= StreamSupport.stream(service.listaNote().spliterator(), false)
                .collect(Collectors.toList());
        model= FXCollections.observableArrayList(list);
    }

    @FXML
    public void handleExit() {
        this.primaryStage.setScene(mainScene);
    }

    public void setService(Service service){
        this.service = service;
    }
}
