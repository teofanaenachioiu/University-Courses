package controller;

import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class LoginController {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainScene;
    private static final String urlAuth = "http://localhost:8080/auth";

    RestTemplate restTemplate = new RestTemplate();

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Label errorLabel;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Utilizator login() throws Exception {
        Utilizator utilizator = Utilizator.builder()
                .username(usernameTextField.getText())
                .password(passwordTextField.getText())
                .build();
        return execute(()-> restTemplate.postForObject(urlAuth + "/login", utilizator, Utilizator.class));
    }

    @FXML
    public void handleLogin() {
        if(usernameTextField.getText().equals("") || passwordTextField.getText().equals("")
                || usernameTextField.getText() == null || passwordTextField.getText() == null){
            this.errorLabel.setText("completeaza toate campurile");
            this.errorLabel.setVisible(true);
        }
        else{
            try {
                Utilizator utilizator = login();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mainView.fxml"));
                AnchorPane rootLayout = loader.load();
                mainScene = new Scene(rootLayout, 600, 400);

                MainController mainController = loader.getController();
                mainController.setPrimaryStage(primaryStage);
                mainController.setLoginScene(this.loginScene);
                mainController.setDataInitializare(utilizator);

                this.primaryStage.setScene(mainScene);
                this.errorLabel.setText("");
                this.errorLabel.setVisible(false);

            } catch (Exception e) {
                this.errorLabel.setText("date invalide");
                this.errorLabel.setVisible(true);
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setMainScene(Scene loginScene){
        this.loginScene = loginScene;
    }
}