package sample;

import domain.Client;
import domain.Location;
import domain.Reservation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import repository.Repository;
import repository.RepositoryInFileClient;
import repository.RepositoryInFileLocation;
import repository.RepositoryInFileReservation;
import service.Service;

import java.util.Locale;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main extends Application {

    private static Service service=new Service();
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        for(Location l:service.getLocatii(args[0]))
            System.out.println(l);
        service.numarRezervari().forEach((x,y)->System.out.println(x+" "+y));

        service.sumaIncasata().entrySet().forEach(
                e->{
                    System.out.println(e.getKey());
                    double suma=e.getValue()
                            .stream()
                            .map(r->r.getLocation().getPricePerNight())
                            .reduce(0d,(r1,r2)->r1+r2);
                    System.out.println(suma);
                }
        );
        
        launch(args);
    }
}
