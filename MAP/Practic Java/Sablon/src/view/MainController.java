package view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private Stage stage;
    private Scene entity1Scene;
    private Scene entity12Scene;

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public void setEntity1Scene(Scene scene){
        this.entity1Scene=scene;
    }

    public  void setEntity12Scene(Scene scene){
        this.entity12Scene=scene;
    }

    @FXML
    private void handleEntity1Button(){
        this.stage.setScene(this.entity1Scene);
    }

    @FXML
    private void handleEntity12Button(){
        this.stage.setScene(this.entity12Scene);
    }
}
