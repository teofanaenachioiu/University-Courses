import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setWidth(200);
        primaryStage.setHeight(200);
        Button b=new Button("ok");
        HBox hb=new HBox();
        hb.getChildren().add(b);
        primaryStage.setScene(new Scene(hb));
        primaryStage.show();

    }
}
