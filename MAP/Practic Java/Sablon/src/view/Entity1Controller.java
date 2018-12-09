package view;

import domain.Entity1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import utils.DataChanged;
import utils.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Entity1Controller implements Observer<DataChanged> {
    private Stage stage;
    private Scene mainScene;
    private Service service;
    ObservableList<Entity1> model= FXCollections.observableArrayList();
    @FXML Button addButtn;
    @FXML Button deleteButton;
    @FXML Button updateButton;
    @FXML Button refreshButton;
    @FXML Button backButton;
    @FXML TableView<Entity1> tableView;
    @FXML TableColumn<Entity1,String> tableColumnId;
    @FXML TableColumn<Entity1,String> tableColumnNume;
    @FXML TableColumn<Entity1,String> tableColumnData;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void initialize(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableView.setItems(model);
    }

    public void initData(Service service) {
        this.service = service;
        model.setAll( StreamSupport.stream(this.service.getAllEntity1().spliterator(),false)
                .collect(Collectors.toList()));
    }

    @FXML private void handleBack(){
        this.stage.setScene(mainScene);
    }

    @Override public void update(DataChanged dataChanged) {

    }
}
