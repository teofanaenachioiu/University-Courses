package controller;

import domain.Coordonator;
import domain.Participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class ControllerLogin {
    private Stage primaryStage;
    private Scene mainScene;
    private Scene coordScene;
    private static final String urlAuth = "http://localhost:8080/auth";

    RestTemplate restTemplate = new RestTemplate();

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Label errorLabel;
    @FXML
    AnchorPane mainPane;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Coordonator login() throws Exception {
        Coordonator coord = Coordonator.builder()
                .username(usernameTextField.getText())
                .password(passwordTextField.getText())
                .build();
        return execute(()-> restTemplate.postForObject(urlAuth + "/login", coord, Coordonator.class));
    }

    @FXML
    public void handleLogin() {
        if(usernameTextField.getText().equals("") || passwordTextField.getText().equals("")
            || usernameTextField.getText() == null || passwordTextField.getText() == null){
            this.errorLabel.setText("Completeaza toate campurile");
            this.errorLabel.setVisible(true);
        }
        else{
            try {
                Coordonator coordonator = login();
                System.out.println(coordonator);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewCoordonator.fxml"));
                AnchorPane rootLayout = loader.load();
                coordScene = new Scene(rootLayout, 600, 400);
                ControllerCoordonator controllerCoordonator = loader.getController();
                controllerCoordonator.setPrimaryStage(primaryStage);
                controllerCoordonator.setLoginScene(this.mainScene);
                controllerCoordonator.setCoordonator(coordonator);
                controllerCoordonator.setData();
                this.primaryStage.setScene(coordScene);
                this.errorLabel.setText("");
                this.errorLabel.setVisible(false);
            } catch (Exception e) {
                this.errorLabel.setText("wrong credentials");
                this.errorLabel.setVisible(true);
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }
}