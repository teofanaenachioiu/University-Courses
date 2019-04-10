package GUI.operator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.io.IOException;

public class ControllerOperator implements IObserver {
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

    private IServer server;
    private User user;

    private Stage primaryStage;
    private Scene loginScene;

    ControllerInscrieri controllerInscrieri;
    ControllerProbe probeController;

    public void setData(IServer server, User user) throws IOException {
        setUser(user);
        setServer(server);
    }

    private void setUser(User user){
        this.user=user;
        usernameLabel.setText("OPERATOR " + this.user.getID());
    }

    private void setServer(IServer server) throws IOException {
        this.server=server;

        FXMLLoader loaderProbe = new FXMLLoader();
        loaderProbe.setLocation(getClass().getResource("/ViewProbe.fxml"));
        this.probePane = loaderProbe.load();
        this.probeController=loaderProbe.getController();
        System.out.println("Init Data controller");
        probeController.initData(this.server);
        System.out.println("Ies din init");


        FXMLLoader loaderInscrieri = new FXMLLoader();
        loaderInscrieri.setLocation(getClass().getResource("/ViewInscrieri.fxml"));
        this.inscrieriPane = loaderInscrieri.load();
        this.controllerInscrieri = loaderInscrieri.getController();
        controllerInscrieri.setData(server,user);

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
        System.out.println("AM IESIT DE LA OPERATOR "+ user.getID());
        try {
            this.server.logout(user);
        } catch (MyAppException e) {
            System.out.println("Am prins eroarea: "+e.getMessage());
        }
        this.primaryStage.setScene(loginScene);
    }


    @Override
    public void update() throws MyAppException {
        controllerInscrieri.update();
        probeController.update();
    }
}
