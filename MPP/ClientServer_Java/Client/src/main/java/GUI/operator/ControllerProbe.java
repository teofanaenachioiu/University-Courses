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
import model.dto.ProbaDTO;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ControllerProbe implements Initializable, IObserver {
    private final ObservableList<ProbaDTO> model = FXCollections.observableArrayList();
    @FXML
    TableView<ProbaDTO> tableView;
    @FXML
    TableColumn<ProbaDTO, String> columnDenumire;
    @FXML
    TableColumn<ProbaDTO, String> columnCategorie;
    @FXML
    TableColumn<ProbaDTO, Integer> columnNumar;

    private IServer server;

    public void initData(IServer server){
        this.server=server;
       try {
            ProbaDTO[] probaDTO=this.server.listaProbeDTO();
            ArrayList<ProbaDTO> items = new ArrayList<>(Arrays.asList(probaDTO));
            model.setAll(items);
        } catch (MyAppException e) {
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
    public void update() throws MyAppException {
        System.out.println("Incerc sa updatez probele");
        ProbaDTO[] probaDTO=this.server.listaProbeDTO();
        ArrayList<ProbaDTO> items = new ArrayList<>(Arrays.asList(probaDTO));
        model.setAll(items);

    }
}
