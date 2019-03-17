package GUI.operator;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Proba;
import service.ServiceOperator;
import utils.DataChanged;
import utils.Observer;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ControllerProbe implements Observer<DataChanged> {
    private final ObservableList<Proba> model = FXCollections.observableArrayList();
    @FXML
    TableView<Proba> tableView;
    @FXML
    TableColumn<Proba, String> columnDenumire;
    @FXML
    TableColumn<Proba, String> columnCategorie;
    @FXML
    TableColumn<Proba, String> columnNumar;

    private ServiceOperator service;

    public void initData(ServiceOperator service){
        this.service=service;
        model.setAll(StreamSupport.stream(this.service.listaProbe().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(model);
    }

    @FXML
    public void initialize() {
        columnNumar.setCellValueFactory(cellData -> {
            Proba current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(""+service.nrParticipantiProba(current_item));
        });
        columnDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        columnCategorie.setCellValueFactory(cellData -> {
            Proba current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(current_item.getCatg().toString());
        });

    }

    @Override
    public void update(DataChanged dataChanged) {
        model.setAll(StreamSupport.stream(this.service.listaProbe().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(model);
    }
}
