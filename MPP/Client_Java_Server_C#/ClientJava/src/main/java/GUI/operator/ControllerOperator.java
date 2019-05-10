package GUI.operator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.teofana.concurs.ConcursService;
import org.teofana.concurs.ObserverService;

import java.io.IOException;

public class ControllerOperator implements ObserverService.Iface {
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

    private ConcursService.Client client;
    private User user;

    private Stage primaryStage;
    private Scene loginScene;

    private ControllerInscrieri controllerInscrieri;
    private ControllerProbe probeController;
    private TServer serverUpdate;
    private Thread threadUpdate;

    public void setData(ConcursService.Client client, User user, TServer server, Thread thread) throws IOException {
        setUser(user);
        setServer(client);
        this.serverUpdate=server;
        this.threadUpdate = thread;
    }

    private void setUser(User user){
        this.user=user;
        Platform.runLater(()->usernameLabel.setText("OPERATOR " + this.user.getUsername()));
    }

    private void setServer(ConcursService.Client client) throws IOException {
        this.client=client;

        FXMLLoader loaderProbe = new FXMLLoader();
        loaderProbe.setLocation(getClass().getResource("/ViewProbe.fxml"));
        this.probePane = loaderProbe.load();
        this.probeController=loaderProbe.getController();
        probeController.initData(this.client);


        FXMLLoader loaderInscrieri = new FXMLLoader();
        loaderInscrieri.setLocation(getClass().getResource("/ViewInscrieri.fxml"));
        this.inscrieriPane = loaderInscrieri.load();
        this.controllerInscrieri = loaderInscrieri.getController();
        this.controllerInscrieri.setData(client,user);
        System.out.println(Platform.isFxApplicationThread());
        Platform.runLater(()->this.viewPane.getChildren().clear());
        Platform.runLater(()->this.viewPane.getChildren().add(probePane));
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
        System.out.println("AM IESIT DE LA OPERATOR "+ user.getUsername());
        try {
            this.client.logout(user);

            if (this.serverUpdate != null && serverUpdate.isServing()) {
                System.out.print("Stopping the server... ");
                serverUpdate.stop();
                System.out.print("Stopping the thread... ");
                threadUpdate.interrupt();
                System.out.println("done.");
            }
        } catch (TException e) {
            e.printStackTrace();
        }
        this.primaryStage.setScene(loginScene);
    }


    @Override
    public void notifyClient() throws TException {
        this.controllerInscrieri.notifyClient();
        this.probeController.notifyClient();
    }
}
