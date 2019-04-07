package GUI.operator;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Proba;
import service.ServiceOperator;
import services.IObserver;
import services.IServer;
import utils.DataChanged;
import utils.Observer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ControllerProbe implements Initializable, IObserver {
    private final ObservableList<Proba> model = FXCollections.observableArrayList();
    @FXML
    TableView<Proba> tableView;
    @FXML
    TableColumn<Proba, String> columnDenumire;
    @FXML
    TableColumn<Proba, String> columnCategorie;
    @FXML
    TableColumn<Proba, String> columnNumar;

    private IServer server;

    public void initData(IServer server){
        this.server=server;
        model.setAll(StreamSupport.stream(this.server.listaProbe().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(model);
    }

    @FXML
    public void initialize() {
        columnNumar.setCellValueFactory(cellData -> {
            Proba current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(""+server.nrParticipantiProba(current_item));
        });
        columnDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        columnCategorie.setCellValueFactory(cellData -> {
            Proba current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(current_item.getCatg().toString());
        });

    }

    @Override
    public void inscriereParticipant() {
        model.setAll(StreamSupport.stream(this.server.listaProbe().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(model);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
