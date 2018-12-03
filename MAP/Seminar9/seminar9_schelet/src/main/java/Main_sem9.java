import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Nota;
import model.NotaDTO;
import noteReports.NoteController;
import noteReports.RepoNote;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main_sem9 extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        reportMedii();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("noteViewFXML.fxml"));
        AnchorPane root=loader.load();
        //NoteController ctrl=loader.getController();


        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("Hello World");
        primaryStage.show();


    }

    private void reportMedii() {
        Map <String, List<Nota>> mapStudent =
                RepoNote.findAll()
                .stream()
                .collect(Collectors.groupingBy(n->n.getStudent().getName()));

        mapStudent.entrySet()
                .forEach(e -> {
                    System.out.println(e.getKey());
                    double suma = e.getValue()
                            .stream()
                            .map(n->n.getValue())
                            .reduce(0d, (n1, n2)->n1+n2);
                    double media = suma / 3;
                    System.out.println(media);
                });


//        mapStudent.entrySet()
//                .stream()
//                .forEach(e->
//                {
////                    System.out.println(e.getKey());
////                    e.getValue()
////                            .forEach(n-> System.out.println(n.getValue()));
//
//                });


    }


    public static void main(String[] args) {
        launch(args);
    }

}
