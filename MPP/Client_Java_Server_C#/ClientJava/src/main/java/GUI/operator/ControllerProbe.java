package GUI.operator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.teofana.concurs.ConcursService;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class ControllerProbe implements Initializable{
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

    public void initData(ConcursService.Client client){
        this.client=client;
       try {
            ProbaDTO[] probaDTO=this.client.listaProbeDTO();
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

//    @Override
//    public void update() throws MyAppException {
//        System.out.println("Incerc sa updatez probele");
//        ProbaDTO[] probaDTO=this.server.listaProbeDTO();
//        ArrayList<ProbaDTO> items = new ArrayList<>(Arrays.asList(probaDTO));
//        model.setAll(items);
//
//    }
}
