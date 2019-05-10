package GUI.operator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProbaDTO;
import org.apache.thrift.TException;
import org.teofana.concurs.ConcursService;
import org.teofana.concurs.ObserverService;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerProbe implements Initializable, ObserverService.Iface {
    private final ObservableList<ProbaDTO> model = FXCollections.observableArrayList();
    @FXML
    TableView<ProbaDTO> tableView;
    @FXML
    TableColumn<ProbaDTO, String> columnDenumire;
    @FXML
    TableColumn<ProbaDTO, String> columnCategorie;
    @FXML
    TableColumn<ProbaDTO, Integer> columnNumar;

    private ConcursService.Client client;

    void initData(ConcursService.Client client){
        this.client=client;
        try {
            List<ProbaDTO> items = this.client.listaProbeDTO();
                model.setAll(items);
            } catch (TException e) {
               e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        columnCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        columnNumar.setCellValueFactory(new PropertyValueFactory<>("nrParticipanti"));
        tableView.setItems(model);
    }

    @Override
    public void notifyClient() throws TException {
        System.out.println("Update probe");
        List<ProbaDTO> items = this.client.listaProbeDTO();
        model.setAll(items);
    }
}
