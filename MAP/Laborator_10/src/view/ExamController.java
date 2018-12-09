package view;

import domain.Media;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import service.Service;
import utils.NotaChangeEvent;
import utils.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExamController implements Observer<NotaChangeEvent> {
    Service service;
    private ObservableList<Media> model= FXCollections.observableArrayList();;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<Media> tableView;
    @FXML
    private TableColumn<Media,String> tableColumnIdStudent;
    @FXML
    private TableColumn<Media,String> tableColumnNumeStudent;
    @FXML
    private TableColumn<Media,String> tableColumnGrupa;
    @FXML
    private TableColumn<Media,String> tableColumnMedia;

    @FXML
    private void initialize(){
        tableColumnIdStudent.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        tableColumnNumeStudent.setCellValueFactory(new PropertyValueFactory<>("numeStudent"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        tableColumnMedia.setCellValueFactory(new PropertyValueFactory<>("media"));

        tableView.setItems(model);
    }

    public void init(Service serv){
        this.service=serv;

        model.setAll(
                StreamSupport
                        .stream(service.medieStudenti().spliterator(),false)
                        .filter(x->x.getMedia()>=4)
                        .collect(Collectors.toList())
        );

    }

    @Override
    public void update(NotaChangeEvent notaChangeEvent) {
        model.setAll(
                StreamSupport
                        .stream(service.medieStudenti().spliterator(),false)
                        .filter(x->x.getMedia()>=4)
                        .collect(Collectors.toList())
        );
    }
}
