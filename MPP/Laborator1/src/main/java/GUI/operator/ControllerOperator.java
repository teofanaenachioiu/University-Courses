package GUI.operator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.ServiceOperator;
import utils.DataChanged;
import utils.Observer;

import java.io.IOException;

public class ControllerOperator implements Observer<DataChanged> {
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane viewPane;
    @FXML
    AnchorPane buttonPane;
    @FXML
    Button inscrieriButton;
    @FXML
    Button logoutButton;
    @FXML
    Button probeButton;
    @FXML
    Label usernameLabel;

    private AnchorPane probePane;
    private AnchorPane inscrieriPane;

    private ServiceOperator service;
    private User user;

    private Stage primaryStage;
    private Scene loginScene;

    public void setData(ServiceOperator service, User user) throws IOException {
        setUser(user);
        setService(service);
    }

    private void setUser(User user){
        this.user=user;
        usernameLabel.setText("OPERATOR " + this.user.getID());
    }

    private void setService(ServiceOperator service) throws IOException {
        this.service=service;

        FXMLLoader loaderProbe = new FXMLLoader();
        loaderProbe.setLocation(getClass().getResource("/ViewProbe.fxml"));
        this.probePane = loaderProbe.load();
        ControllerProbe probeController=loaderProbe.getController();
        probeController.initData(this.service);
        this.service.addObserver(probeController);

//        FXMLLoader loaderCategorii = new FXMLLoader();
//        loaderCategorii.setLocation(getClass().getResource("/ViewCategorii.fxml"));
//        this.categoriiPane = loaderCategorii.load();

        FXMLLoader loaderInscrieri = new FXMLLoader();
        loaderInscrieri.setLocation(getClass().getResource("/ViewInscrieri.fxml"));
        this.inscrieriPane = loaderInscrieri.load();
        ControllerInscrieri controllerInscrieri = loaderInscrieri.getController();
        controllerInscrieri.setData(service,user);
        this.service.addObserver(controllerInscrieri);

//        FXMLLoader loaderCautare = new FXMLLoader();
//        loaderCautare.setLocation(getClass().getResource("/ViewCautare.fxml"));
//        this.cautarePane = loaderCautare.load();
//        ControllerCautare cautareController=loaderCautare.getController();
//        cautareController.initData(service);
//        this.service.addObserver(cautareController);

        this.viewPane.getChildren().clear();
        this.viewPane.getChildren().add(probePane);
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage=primaryStage;
    }

    public void setLoginScene(Scene scene){
        this.loginScene=scene;
    }

    @FXML
    public void handleProbePane()  {
        this.viewPane.getChildren().clear();
        this.viewPane.getChildren().add(probePane);
    }

    @FXML
    public void handleInscrieriPane() {
        this.viewPane.getChildren().clear();
        this.viewPane.getChildren().add(inscrieriPane);
    }

    @FXML
    public void handleLogout(){
        this.primaryStage.setScene(loginScene);
    }

    @Override
    public void update(DataChanged dataChanged) {

    }
}
