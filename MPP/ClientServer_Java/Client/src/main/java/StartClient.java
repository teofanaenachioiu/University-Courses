import GUI.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rpcprotocol.ServerRpcProxy;
import services.IServer;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServer server = new ServerRpcProxy(serverIP, serverPort);

        System.out.println("Hello!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewLogin.fxml"));
        AnchorPane rootLayout = loader.load();
        Scene mainMenuScene = new Scene(rootLayout, 600, 400);

        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.init(server, mainMenuScene);

        controllerLogin.setPrimaryStage(primaryStage);

        primaryStage.setScene(mainMenuScene);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Concurs");

        primaryStage.show();
    }
}
