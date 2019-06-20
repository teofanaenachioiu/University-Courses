package controller;

import domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class MainController implements Initializable {
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
//    private final ObservableList<> model = FXCollections.observableArrayList();
//    private static final String urlServer = "http://localhost:8080";
    RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleLogout() {
        this.primaryStage.setScene(loginScene);
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void setLoginScene(Scene mainScene) {
        this.loginScene = mainScene;
    }

    void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
        this.utilizatorLabel.setText("Custom label");
    }
}
