package GUI.operator;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Categorie;
import model.Participant;
import model.Proba;
import model.User;
import org.controlsfx.control.CheckComboBox;
import repository.RepositoryException;
import service.ServiceOperator;
import utils.AutoCompleteComboBoxListener;
import utils.DataChanged;
import utils.GUIutils;
import utils.Observer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerInscrieri implements Observer<DataChanged> {
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

    ServiceOperator service;
    User user;
    @FXML
    public void initialize() {
        columnVarsta.setCellValueFactory(cellData -> {
            Participant current_item = cellData.getValue();
            return new ReadOnlyStringWrapper(""+current_item.getVarsta());
        });
        columnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        this.comboBoxCategorie.valueProperty().addListener(o->handleCauta());
        this.comboBoxProba.valueProperty().addListener(o->handleCauta());
    }

    public void setData(ServiceOperator service, User user){
        this.user=user;
        this.service=service;
        final ObservableList<Proba> probe = FXCollections.observableArrayList();
        probe.setAll((Collection<? extends Proba>) service.listaProbe());

        checkComboBox.getItems().setAll(probe);
//        checkComboBox.getCheckModel()
//                .getCheckedItems()
//                .addListener((ListChangeListener<? super Proba>) o->handleMarcat());
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 15);

        spinnerVarsta.setValueFactory(valueFactory);

        modelCaut.setAll(StreamSupport.stream(this.service.listaParticipanti().spliterator(),false)
                .collect(Collectors.toList()));
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
            service.inscriereParticipant(nume,varsta,probe11,this.user.getID());
            GUIutils.showInfoMessage("Participantul a fost inscris");
            this.fieldNume.setText(null);
        }
        catch (RepositoryException e){
            GUIutils.showErrorMessage(e.getMessage());
        }
    }

    @Override
    public void update(DataChanged dataChanged) {
        modelCaut.setAll(StreamSupport.stream(this.service.listaParticipanti().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(modelCaut);
        initComboBox();
    }

    @FXML
    public void handleCauta(){
        String denumireProba=this.comboBoxProba.getSelectionModel().getSelectedItem();
//        if(this.comboBoxProba.getSelectionModel().getSelectedItem()==null)
//            denumireProba="";
//        else denumireProba=this.comboBoxProba.getSelectionModel().getSelectedItem();
        String categorie;
        if(this.comboBoxCategorie.getSelectionModel().getSelectedItem()==null)
            categorie=null;
        else categorie=this.comboBoxCategorie.getSelectionModel().getSelectedItem().toString();

        modelCaut.setAll(StreamSupport
                .stream(service.filtreazaParticipantiKeyword(denumireProba,categorie).spliterator(),false)
                .collect(Collectors.toList()));

        tableView.setItems(modelCaut);
    }

    @FXML
    public void handleReset(){
        this.comboBoxProba.setValue(null);
        this.comboBoxCategorie.setValue(null);
        modelCaut.setAll(StreamSupport.stream(this.service.listaParticipanti().spliterator(),false)
                .collect(Collectors.toList()));
        tableView.setItems(modelCaut);
    }

    private void initComboBox(){
        List<String> lista= StreamSupport.stream(service.listaProbeNume().spliterator(),false)
                .collect(Collectors.toList());
        ObservableList<String> data = FXCollections.observableArrayList(lista);
        this.comboBoxProba.setItems(data);

        List<Categorie> lista1=StreamSupport.stream(service.listaCategorii().spliterator(),false)
                .collect(Collectors.toList());
        ObservableList<Categorie> data1 = FXCollections.observableArrayList(lista1);
        this.comboBoxCategorie.setItems(data1);
        //this.comboBoxCategorie.setValue(lista1.get(0));
        //new AutoCompleteComboBoxListener<>(this.comboBoxCategorie);
        new AutoCompleteComboBoxListener<>(this.comboBoxProba);

    }

}
