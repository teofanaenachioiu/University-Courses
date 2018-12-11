package view;

import domain.Entity1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.stream.StreamSupport;

public class Controller {
    private Service service;
//    ObservableList<Entity1> model= FXCollections.observableArrayList();
//    @FXML
//    TableView<Entity1> tableView;
//    @FXML TableColumn<Entity1,String> tableColumnId;
//    @FXML TableColumn<Entity1,String> tableColumnNume;
//    @FXML TableColumn<Entity1,String> tableColumnData;

//    @FXML Button button;
//    @FXML ComboBox<String> comboBox;
//    @FXML TextField textField;
//    @FXML TextArea textArea;
//    @FXML CheckBox checkBox;


    @FXML private void initialize(){
//        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
//        tableView.setItems(model);

        // init COMBOBOX
//        ObservableList<String> data = FXCollections.observableArrayList(
//                "221", "222", "223", "224", "225", "226", "227");
//        this.comboBox.setItems(data);

        //COMBOBOX Filtrari
//        this.comboBox.valueProperty().addListener(o->handleFilter());
    }
//
    public void initData(Service service) {
       this.service = service;
//        model.setAll( StreamSupport.stream(this.service.getAllEntity1().spliterator(),false)
//                .collect(Collectors.toList()));

        // init COMBOBOX
//        List<String> lista= StreamSupport.stream(service..spliterator(),false).
//                map(student->student.getNume()+" (ID:"+student.getID()+")").collect(Collectors.toList());
//        ObservableList<String> data = FXCollections.observableArrayList(lista);
//
//        this.comboBox.setItems(data);
    }

    @FXML
    private void handleFilter() {
//        Predicate<NotaDTO> p4 = n->
//                service.cautaNota(n.getIdStudent(),n.getIdTema())
//                        .getDataCurenta().isEmpty() ||
//                        this.datePickerFilter1.getValue()==null ||
//                        this.datePickerFilter2.getValue()==null ||
//                        Integer.parseInt(service.cautaNota(n.getIdStudent(),n.getIdTema()).getDataCurenta()) >=getDate1() &&
//                                Integer.parseInt(service.cautaNota(n.getIdStudent(),n.getIdTema()).getDataCurenta()) <=getDate2();
//
//        Predicate<NotaDTO> p1 = n->
//                this.comboBoxLabFilter.getSelectionModel().getSelectedItem()==null ||
//                        n.getIdTema()
//                                .contains(this.comboBoxLabFilter.getSelectionModel().getSelectedItem());
//
//        Predicate<NotaDTO> p2 = n->
//                this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()==null ||
//                        n.getNumeStudent().contains(this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()) ||
//                        this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()
//                                .contains(n.getNumeStudent());
//
//        Predicate<NotaDTO> p3 = n->
//                this.comboBoxGroupFilter.getSelectionModel().getSelectedItem()==null ||
//                        this.service.cautaStudent(n.getIdStudent()).get().getGrupa()
//                                .contains(this.comboBoxGroupFilter.getSelectionModel().getSelectedItem());
//
//        model.setAll(service.listaNoteDTO().stream()
//                .filter(p4.and(p3).and(p2).and(p1))
//                .collect(Collectors.toList()));
    }

}
