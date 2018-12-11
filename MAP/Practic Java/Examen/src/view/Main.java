package view;

import domain.Entity;
import domain.Tichet;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import service.Service;

import java.util.Collection;
import java.util.stream.StreamSupport;

public class Main extends Application {
    Service service=new Service();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("view.fxml"));
        AnchorPane rootLayout=loader.load();
        Scene scene=new Scene(rootLayout,500,350);

        Controller controller=loader.getController();
        controller.initData(service);
        primaryStage.setTitle("Entities");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
//        for(Location l:service.getLocatii(args[0]))
//            System.out.println(l);

//        service.numarRezervari().forEach((x,y)->System.out.println(x+" "+y));
//
//        service.sumaIncasata().entrySet().forEach(
//                e->{
//                    System.out.println(e.getKey());
//                    double suma=e.getValue()
//                            .stream()
//                            .map(r->r.getLocation().getPricePerNight())
//                            .reduce(0d,(r1,r2)->r1+r2);
//                    System.out.println(suma);
//                }
//        );

       // Repository<String, User> repo=new RepositoryInFileUser("./src/data/users.txt");
//        Repository<Integer, Tichet> repository=new RepositoryInFileTichet("./src/data/tickets.txt");
//        Collection<Tichet> re=repository.findAll();


        Service serv=new Service();
        Integer total=serv.getNumarUsers();
        serv.nrUtilizatori().forEach((x,y)->System.out.println(x+ " "+y+"/"+total));
        System.out.println("Cost mediu: ");
        serv.costMediu().entrySet().forEach(
                e->{
                    System.out.print(e.getKey()+": ");
                    double suma=e.getValue()
                            .stream()
                            .map(r->r.getCost())
                            .reduce(0d,(r1,r2)->r1+r2);

                    System.out.println(suma/3);
                }
        );

        System.out.println("Raport: ");
        serv.listaRaport().forEach(x->System.out.println(x));
        System.out.println("hello");
        launch(args);
    }
}
