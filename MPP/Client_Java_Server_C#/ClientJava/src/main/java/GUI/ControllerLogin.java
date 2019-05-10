package GUI;

import GUI.operator.ControllerOperator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.TipUser;
import model.User;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.teofana.concurs.ConcursService;
import org.teofana.concurs.MyAppException;
import org.teofana.concurs.ObserverService;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    TextField fieldUser;
    @FXML
    PasswordField fieldPassword;
    @FXML
    Label errorLabel;

    private ConcursService.Client client;

    private Stage primaryStage;
    private Scene operatorScene;
    private Scene loginScene;
    private int myUpdatePort;
    TServer server;

    public void init(ConcursService.Client client, Scene loginScene) {
        this.client = client;
        this.loginScene = loginScene;
        this.errorLabel.setVisible(false);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }

    @FXML
    public void handleLogin() throws IOException {
        String username = "";
        String password = "";

        errorLabel.setVisible(false);

        if (fieldUser.getText() != null)
            username = fieldUser.getText().trim();
        if (fieldPassword.getText() != null)
            password = fieldPassword.getText();

        try {
            myUpdatePort = client.login(username, password);

            User utilizator = client.cauta(username);

            if (utilizator != null) {
                if (utilizator.getTipUser().equals(TipUser.ADMIN)) {
                    System.out.println("AM INTRAT LA ADMIN");
                } else if (utilizator.getTipUser().equals(TipUser.OPERATOR)) {
                    System.out.println("AM INTRAT LA OPERATOR");

                    ControllerOperator controllerOperator = initOperatorWindow();

                    Thread thread = new Thread() {
                        public void run() {
                            System.out.println("Thread Running for Updates");
                            TServerTransport serverTransport = null;
                            try {
                                serverTransport = new TServerSocket(myUpdatePort);
                                server = new TSimpleServer(new TServer.Args(serverTransport)
                                        .processor(new ObserverService.Processor<>(controllerOperator)));
                                controllerOperator.setData(client, utilizator, server, this);
                                System.out.print("Starting the server for updates... ");
                                server.serve();
                                System.out.println("done.");

                            } catch (TTransportException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                    handleOperatorWindow();
                }
            }
        } catch (MyAppException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        } catch (TException e) {
            e.printStackTrace();
        }

        fieldPassword.clear();
        fieldUser.setText(null);
        fieldUser.requestFocus();
    }

    private ControllerOperator initOperatorWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewOperator.fxml"));
        AnchorPane operatorLayout = loader.load();

        Scene sceneOperator = new Scene(operatorLayout, 600, 400);
        ControllerOperator controllerOperator = loader.getController();

        controllerOperator.setLoginScene(loginScene);
        controllerOperator.setPrimaryStage(primaryStage);

        this.operatorScene = sceneOperator;
        return controllerOperator;
    }

    private void handleOperatorWindow() {
        this.primaryStage.setScene(this.operatorScene);
    }

}
