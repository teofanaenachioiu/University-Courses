package view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {
    private Stage primaryStage;
    private Scene studentScene;
    private Scene gradeScene;

    public void setStudentScene(Scene studentScene) {
        this.studentScene = studentScene;
    }

    public void setGradeStage(Scene gradeScene) {
        this.gradeScene = gradeScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    @FXML
    public void handleStudentCRUD()
    {
        this.primaryStage.setScene(this.studentScene);
    }

    @FXML
    public void handleGradeCRUD()
    {
        this.primaryStage.setScene(this.gradeScene);
    }


}
