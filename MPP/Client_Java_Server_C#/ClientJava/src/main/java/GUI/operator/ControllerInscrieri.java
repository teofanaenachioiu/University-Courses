package GUI.operator;


import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Participant;
import model.Proba;
import model.User;
import org.apache.thrift.TException;
import org.controlsfx.control.CheckComboBox;
import org.teofana.concurs.ConcursService;
import org.teofana.concurs.MyAppException;
import org.teofana.concurs.ObserverService;
import utils.AutoCompleteComboBoxListener;
import utils.GUIutils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerInscrieri implements Initializable, ObserverService.Iface {
    @FXML
    TextField fieldNume;
    @FXML
    Spinner spinnerVarsta;
    @FXML
    CheckComboBox<Proba> checkComboBox;
    @FXML
    Button buttonInscriere;
    private final ObservableList<Participant> modelCaut = FXCollections.observableArrayList();
    @FXML
    TableView<Participant> tableView;
    @FXML
    TableColumn<Participant, String> columnNume;
    @FXML
    TableColumn<Participant, String> columnVarsta;
    @FXML
    ComboBox<String> comboBoxProba;
    @FXML
    ComboBox<String> comboBoxCategorie;
    @FXML
    Button buttonCauta;

    private ConcursService.Client client;
    private User user;

    void setData(ConcursService.Client client, User user){
        this.user=user;
        this.client=client;
        final ObservableList<Proba> probe = FXCollections.observableArrayList();
        try {
            probe.setAll(client.listaProbe());
        } catch (TException e) {
            e.printStackTrace();
        }

        checkComboBox.getItems().setAll(probe);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 15);

        spinnerVarsta.setValueFactory(valueFactory);

        try {
            modelCaut.setAll(new ArrayList<>(this.client.listaParticipanti()));
        } catch (TException e) {
            e.printStackTrace();
        }
        tableView.setItems(modelCaut);
        initComboBox();
    }

    public void handleMarcat(){
        ObservableList<Proba> probe11=checkComboBox.getCheckModel().getCheckedItems();
        int varsta = Integer.parseInt(spinnerVarsta.getValue().toString());
        String nume = fieldNume.getText();
        if(nume==null || nume.equals(""))
            GUIutils.showErrorMessage("Nu ati introdus numele participantului");
        if(probe11.size()==0)
            GUIutils.showErrorMessage("Nu s-a selectat probe");

        try {
            client.inscriereParticipant(nume, varsta, probe11, this.user.getUsername());
            GUIutils.showInfoMessage("Participantul a fost inscris");
            this.fieldNume.setText(null);
        } catch (MyAppException e){
            GUIutils.showErrorMessage(e.getMessage());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCauta(){
        String denumireProba=this.comboBoxProba.getSelectionModel().getSelectedItem();
        String categorie;
        if(this.comboBoxCategorie.getSelectionModel().getSelectedItem()==null)
            categorie=null;
        else categorie=this.comboBoxCategorie.getSelectionModel().getSelectedItem().toString();

        try {
            modelCaut.setAll(new ArrayList<>(client.filtreazaParticipantiKeyword(denumireProba, categorie)));
        } catch (TException e) {
            e.printStackTrace();
        }

        tableView.setItems(modelCaut);
    }

    @FXML
    public void handleReset(){
        this.comboBoxProba.setValue(null);
        this.comboBoxCategorie.setValue(null);
        try {
            modelCaut.setAll(new ArrayList<>(this.client.listaParticipanti()));
        } catch (TException e) {
            e.printStackTrace();
        }
        tableView.setItems(modelCaut);
    }

    private void initComboBox(){
        List<String> lista= null;
        try {
            lista = new ArrayList<>(client.listaProbeNume());
        } catch (TException e) {
            e.printStackTrace();
        }
        ObservableList<String> data = FXCollections.observableArrayList(lista);
        this.comboBoxProba.setItems(data);

        List<String> lista1= null;
        try {
            lista1 = new ArrayList<>(client.listaCategorii());
        } catch (TException e) {
            e.printStackTrace();
        }
        ObservableList<String> data1 = FXCollections.observableArrayList(lista1);
        this.comboBoxCategorie.setItems(data1);
        new AutoCompleteComboBoxListener<>(this.comboBoxProba);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnVarsta.setCellValueFactory(cellData -> {
            Participant current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(""+current_item.getVarsta());
        });
        columnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        this.comboBoxCategorie.valueProperty().addListener(o->handleCauta());
        this.comboBoxProba.valueProperty().addListener(o->handleCauta());
    }

    @Override
    public void notifyClient() throws TException {
        System.out.println("Update participanti");
        modelCaut.setAll(new ArrayList<>(this.client.listaParticipanti()));
        tableView.setItems(modelCaut);
    }
}
