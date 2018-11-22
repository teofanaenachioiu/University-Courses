package sem8_FXML.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuController_V2 {

    private Stage primaryStage;
    private Scene messageTaskScene;
    private Scene gradeScence;

    public void setMessageScene(Scene messageTaskScene) {
        this.messageTaskScene = messageTaskScene;
    }

    public void setGradeScene(Scene gradeScence) {
        this.gradeScence = gradeScence;
    }

    @FXML
    public void handleMessageTaskCRUD()
    {
        this.primaryStage.setScene(messageTaskScene);
    }

    @FXML
    public void handleGradeCRUD()
    {
        this.primaryStage.setScene(messageTaskScene);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }
}
