package GUI.operator;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Categorie;
import model.Participant;
import model.Proba;
import model.User;
import org.controlsfx.control.CheckComboBox;
import repository.RepositoryException;
import services.IObserver;
import services.IServer;
import services.MyAppException;
import utils.AutoCompleteComboBoxListener;
import utils.GUIutils;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerInscrieri implements Initializable, IObserver {
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
    ComboBox<Categorie> comboBoxCategorie;
    @FXML
    Button buttonCauta;

    IServer server;
    User user;

    public void setData(IServer server, User user){
        this.user=user;
        this.server=server;
        final ObservableList<Proba> probe = FXCollections.observableArrayList();
        try {
            probe.setAll((Collection<? extends Proba>) server.listaProbe());
        } catch (MyAppException e) {
            e.printStackTrace();
        }

        checkComboBox.getItems().setAll(probe);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 15);

        spinnerVarsta.setValueFactory(valueFactory);

        try {
            modelCaut.setAll(StreamSupport.stream(this.server.listaParticipanti().spliterator(),false)
                    .collect(Collectors.toList()));
        } catch (MyAppException e) {
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
        try{
            server.inscriereParticipant(nume,varsta,probe11,this.user.getID());
            GUIutils.showInfoMessage("Participantul a fost inscris");
            this.fieldNume.setText(null);
        }
        catch (MyAppException e){
            GUIutils.showErrorMessage(e.getMessage());
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
            modelCaut.setAll(StreamSupport
                    .stream(server.filtreazaParticipantiKeyword(denumireProba,categorie).spliterator(),false)
                    .collect(Collectors.toList()));
        } catch (MyAppException e) {
            e.printStackTrace();
        }

        tableView.setItems(modelCaut);
    }

    @FXML
    public void handleReset(){
        this.comboBoxProba.setValue(null);
        this.comboBoxCategorie.setValue(null);
        try {
            modelCaut.setAll(StreamSupport.stream(this.server.listaParticipanti().spliterator(),false)
                    .collect(Collectors.toList()));
        } catch (MyAppException e) {
            e.printStackTrace();
        }
        tableView.setItems(modelCaut);
    }

    private void initComboBox(){
        List<String> lista= null;
        try {
            lista = StreamSupport.stream(server.listaProbeNume().spliterator(),false)
                    .collect(Collectors.toList());
        } catch (MyAppException e) {
            e.printStackTrace();
        }
        ObservableList<String> data = FXCollections.observableArrayList(lista);
        this.comboBoxProba.setItems(data);

        List<Categorie> lista1= null;
        try {
            lista1 = StreamSupport.stream(server.listaCategorii().spliterator(),false)
                    .collect(Collectors.toList());
        } catch (MyAppException e) {
            e.printStackTrace();
        }
        ObservableList<Categorie> data1 = FXCollections.observableArrayList(lista1);
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
    public void update() throws MyAppException {
        System.out.println("Incerc sa updatez participantii");
        modelCaut.setAll(StreamSupport.stream(this.server.listaParticipanti().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(modelCaut);
    }
}
