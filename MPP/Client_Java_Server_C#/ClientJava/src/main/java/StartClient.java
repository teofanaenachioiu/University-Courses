import GUI.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;
import org.teofana.concurs.ConcursService;

public class StartClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TTransport transport = new TSocket("localhost", 9095);
        transport.open();
        TProtocol protocol = new  TBinaryProtocol(transport);
        ConcursService.Client client = new ConcursService.Client(protocol);
        //System.out.println("Hello");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewLogin.fxml"));
        AnchorPane rootLayout = loader.load();
        Scene mainMenuScene = new Scene(rootLayout, 600, 400);

        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.init(client, mainMenuScene);

        controllerLogin.setPrimaryStage(primaryStage);

        primaryStage.setScene(mainMenuScene);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Concurs");

        primaryStage.show();
    }
}
