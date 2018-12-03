package view;

import domain.Nota;
import domain.NotaDTO;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.ValidationException;
import service.Service;
import utils.ChangeEventType;
import utils.NotaChangeEvent;
import utils.Observer;

import javax.management.StandardEmitterMBean;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController implements Observer<NotaChangeEvent> {
    public BorderPane GradeBorderPane;
    public Button imageButton;
    private Service service;
    private ObservableList<NotaDTO> model;

    private Stage primaryStage;
    private Scene mainScene;

    @FXML
    private TableView<NotaDTO> tableView;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnId;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnNume;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnTema;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnNota;
    @FXML
    private Button addGradeButton;
    @FXML
    private Button filterButton;
    @FXML
    private Label labelLab;
    @FXML
    private Label labelStudent;
    @FXML
    private Label labelGrade;
    @FXML
    private Label labelFeedback;
    @FXML
    private ComboBox<String> comboBoxLab;
    @FXML
    private ComboBox<String> comboBoxNumeStudent;
    //setezi date pt combobox
    //apelezi wrapper de autoComplete pe comboBox
    @FXML
    private TextField textFieldGrade;
    @FXML
    private TextArea textAreaFeedback;
    @FXML
    private Button addButton;
    @FXML
    private CheckBox checkBoxMotivation;




    @FXML
    private ChoiceBox<String> choiceBoxFilter;
    @FXML
    private Label LabelFilter;
    @FXML
    private Label labelLabFilter;
    @FXML
    private Label labelStudentFilter;
    @FXML
    private Label labelGroupFilter;
    @FXML
    private ComboBox<String>comboBoxLabFilter;
    @FXML
    private ComboBox<String> comboBoxNumeFilter;
    @FXML
    private ComboBox<String> comboBoxGroupFilter;
    @FXML
    private Label labelTimeFilter;
    @FXML
    private DatePicker datePickerFilter1;
    @FXML
    private DatePicker datePickerFilter2;
    @FXML
    private Button buttonClear;

    public GradeController() {
        model= FXCollections.observableArrayList();
    }


    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    public void initComboBox(){

        List<String> lista= StreamSupport.stream(service.listaStudenti().spliterator(),false).
                map(student->student.getNume()+" (ID:"+student.getID()+")").collect(Collectors.toList());
        ObservableList<String> data = FXCollections.observableArrayList(lista);

        this.comboBoxNumeStudent.setItems(data);
        this.comboBoxNumeFilter.setItems(data);
        new AutoCompleteComboBoxListener<>(this.comboBoxNumeStudent);
        new AutoCompleteComboBoxListener<>(this.comboBoxNumeFilter);

        ObservableList<String> dataFilter = FXCollections.observableArrayList(
                "221",
                "222",
                "223",
                "224",
                "225",
                "226",
                "227"
        );
        this.comboBoxGroupFilter.setItems(dataFilter);
    }

    @FXML
    private void initialize(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("numeStudent"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<>("idTema"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        tableView.setItems(model);

        ObservableList<String> data = FXCollections.observableArrayList("1", "2", "3","4","5","6","7","8");
        this.comboBoxLab.setItems(data);

        ObservableList<String> dataFilter = FXCollections.observableArrayList(
                "laboratory",
                "student",
                "group and laboratory",
                "time"
        );
        this.choiceBoxFilter.setItems(dataFilter);

        this.comboBoxLabFilter.setItems(data);

        this.labelLab.setVisible(false);
        this.labelGrade.setVisible(false);
        this.labelStudent.setVisible(false);
        this.labelFeedback.setVisible(false);
        this.comboBoxLab.setVisible(false);
        this.textFieldGrade.setVisible(false);
        this.comboBoxNumeStudent.setVisible(false);
        this.textAreaFeedback.setVisible(false);
        this.addButton.setVisible(false);
        this.checkBoxMotivation.setVisible(false);

        this.LabelFilter.setVisible(false);
        this.choiceBoxFilter.setVisible(false);
        this.labelLabFilter.setVisible(false);
        this.labelStudentFilter.setVisible(false);
        this.labelGroupFilter.setVisible(false);
        this.labelTimeFilter.setVisible(false);
        this.comboBoxLabFilter.setVisible(false);
        this.comboBoxNumeFilter.setVisible(false);
        this.comboBoxGroupFilter.setVisible(false);
        this.datePickerFilter1.setVisible(false);
        this.datePickerFilter2.setVisible(false);
        this.buttonClear.setVisible(false);

        this.comboBoxNumeFilter.valueProperty().addListener(o->handleFilter());
        this.comboBoxLabFilter.valueProperty().addListener(o->handleFilter());
        this.comboBoxGroupFilter.valueProperty().addListener(o->handleFilter());
        this.datePickerFilter1.valueProperty().addListener(o->handleFilter());
        this.datePickerFilter2.valueProperty().addListener(o->handleFilter());
    }

    @FXML
    private void handleAddGradeButton(){
        if(this.labelLab.isVisible()) {
            this.labelLab.setVisible(false);
            this.labelGrade.setVisible(false);
            this.labelStudent.setVisible(false);
            this.labelFeedback.setVisible(false);
            this.comboBoxLab.setVisible(false);
            this.textFieldGrade.setVisible(false);
            this.comboBoxNumeStudent.setVisible(false);
            this.textAreaFeedback.setVisible(false);
            this.addButton.setVisible(false);
            this.checkBoxMotivation.setVisible(false);

            this.LabelFilter.setVisible(false);
            this.choiceBoxFilter.setVisible(false);
            this.labelLabFilter.setVisible(false);
            this.labelStudentFilter.setVisible(false);
            this.labelGroupFilter.setVisible(false);
            this.labelTimeFilter.setVisible(false);
            this.comboBoxLabFilter.setVisible(false);
            this.comboBoxNumeFilter.setVisible(false);
            this.comboBoxGroupFilter.setVisible(false);
            this.datePickerFilter1.setVisible(false);
            this.datePickerFilter2.setVisible(false);
            this.buttonClear.setVisible(false);
        }
        else {
            this.labelLab.setVisible(true);
            this.labelGrade.setVisible(true);
            this.labelStudent.setVisible(true);
            this.labelFeedback.setVisible(true);
            this.comboBoxLab.setVisible(true);
            this.textFieldGrade.setVisible(true);
            this.comboBoxNumeStudent.setVisible(true);
            this.textAreaFeedback.setVisible(true);
            this.addButton.setVisible(true);
            this.checkBoxMotivation.setVisible(true);

            this.LabelFilter.setVisible(false);
            this.choiceBoxFilter.setVisible(false);
            this.labelLabFilter.setVisible(false);
            this.labelStudentFilter.setVisible(false);
            this.labelGroupFilter.setVisible(false);
            this.labelTimeFilter.setVisible(false);
            this.comboBoxLabFilter.setVisible(false);
            this.comboBoxNumeFilter.setVisible(false);
            this.comboBoxGroupFilter.setVisible(false);
            this.datePickerFilter1.setVisible(false);
            this.datePickerFilter2.setVisible(false);
            this.buttonClear.setVisible(false);
        }
    }

    @FXML
    private void handleFilterButton(){
        if(LabelFilter.isVisible()){
            this.LabelFilter.setVisible(false);
            this.choiceBoxFilter.setVisible(false);
            this.labelLabFilter.setVisible(false);
            this.labelStudentFilter.setVisible(false);
            this.labelGroupFilter.setVisible(false);
            this.labelTimeFilter.setVisible(false);
            this.comboBoxLabFilter.setVisible(false);
            this.comboBoxNumeFilter.setVisible(false);
            this.comboBoxGroupFilter.setVisible(false);
            this.datePickerFilter1.setVisible(false);
            this.datePickerFilter2.setVisible(false);
            this.buttonClear.setVisible(false);

            this.labelLab.setVisible(false);
            this.labelGrade.setVisible(false);
            this.labelStudent.setVisible(false);
            this.labelFeedback.setVisible(false);
            this.comboBoxLab.setVisible(false);
            this.textFieldGrade.setVisible(false);
            this.comboBoxNumeStudent.setVisible(false);
            this.textAreaFeedback.setVisible(false);
            this.addButton.setVisible(false);
            this.checkBoxMotivation.setVisible(false);
        }
        else{
            this.LabelFilter.setVisible(true);
            this.choiceBoxFilter.setVisible(true);
            this.labelLabFilter.setVisible(true);
            this.labelStudentFilter.setVisible(true);
            this.labelGroupFilter.setVisible(true);
            this.labelTimeFilter.setVisible(true);
            this.comboBoxLabFilter.setVisible(true);
            this.comboBoxNumeFilter.setVisible(true);
            this.comboBoxGroupFilter.setVisible(true);
            this.datePickerFilter1.setVisible(true);
            this.datePickerFilter2.setVisible(true);
            this.buttonClear.setVisible(true);

            this.labelLab.setVisible(false);
            this.labelGrade.setVisible(false);
            this.labelStudent.setVisible(false);
            this.labelFeedback.setVisible(false);
            this.comboBoxLab.setVisible(false);
            this.textFieldGrade.setVisible(false);
            this.comboBoxNumeStudent.setVisible(false);
            this.textAreaFeedback.setVisible(false);
            this.addButton.setVisible(false);
            this.checkBoxMotivation.setVisible(false);
        }
    }

    private Nota extractNota(){
        String idLab=this.comboBoxLab.getValue();
        String student=this.comboBoxNumeStudent.getValue();
        if(student==null) return null;
        String nota=this.textFieldGrade.getText();
        if(nota==null) return null;
        String idStudent=service.getIdStudent(student);
        return new Nota(idStudent,idLab,service.getLabNumber().toString(),nota);
    }

    private void showDetails(){
        this.comboBoxLab.getSelectionModel().select(service.getCurrentAssignment()-1);
        this.comboBoxNumeStudent.getSelectionModel().select(null);
        this.textAreaFeedback.setText("");
        this.textFieldGrade.setText("");
    }

    @FXML
    public void handleComboLab(){
        Integer assignment=service.getCurrentAssignment();
        Integer lab=Integer.parseInt(this.comboBoxLab.getValue());

        Integer dif=assignment-lab;

        boolean motivat=this.checkBoxMotivation.isSelected();

        if(!motivat &&dif>0 && dif<=2){
            this.textAreaFeedback.setText("NOTA A FOST DIMINUATĂ CU "+dif*2.5f +"\n" +
                    "PUNCTE DATORITĂ ÎNTÂRZIERILOR");
        }

        if(!motivat && dif>2){
            this.textAreaFeedback.setText("SE ACORDA NOTA DIN OFICIU");
        }
        if(dif==0 || motivat){
            this.textAreaFeedback.setText("");
        }
    }

    public void handleMotivation(){
        boolean motivat=this.checkBoxMotivation.isSelected();
        if(motivat) this.textAreaFeedback.setText("");
        else handleComboLab();
    }


    @FXML
    public void handleAddButton(){
        Nota nota=extractNota();
        boolean motivat=this.checkBoxMotivation.isSelected();
        String feedback=this.textAreaFeedback.getText();

        if(nota==null) showErrorMessage("Nu ati introdus date!!");
        else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/ConfirmView.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 400, 250);

                Stage stage = new Stage();
                stage.setTitle("Confirmation");
                stage.setScene(scene);

                stage.show();

                ConfirmController gradeController = fxmlLoader.getController();



                gradeController.init(stage, service, nota,motivat,feedback,this);
            } catch (IOException e) {
                System.out.println("di ce nu mergi?");
            }
        }
    }



    @FXML
    public void handleExit() {
        this.primaryStage.setScene(mainScene);
    }

    public void setService(Service service){
        this.service = service;
        List<NotaDTO> list= StreamSupport.stream(service.listaNoteDTO().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);
        int nrAssignment=service.getCurrentAssignment();
        //MAi trebuie sa pun date in tabeleleaaaaaaaaaaaaaaaa

        this.comboBoxLab.getSelectionModel().select(nrAssignment-1);
        initComboBox();

    }

    public static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    public static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    @Override
    public void update(NotaChangeEvent notaChangeEvent) {
        List<NotaDTO> list= StreamSupport.stream(service.listaNoteDTO().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);

    }

    @FXML
    private void handleFilter() {
        Predicate<NotaDTO> p1 = n->n.getIdTema().isEmpty() || n.getIdTema()
                .equals(comboBoxLabFilter.getSelectionModel().getSelectedItem());

        Predicate<NotaDTO> p2 = n->n.getNumeStudent().isEmpty() || comboBoxNumeFilter.getSelectionModel().getSelectedItem()
                .contains(n.getNumeStudent());

        Predicate<NotaDTO> p3 = n->service.cautaStudent(n.getIdStudent()).get().getGrupa().isEmpty()
                || service.cautaStudent(n.getIdStudent()).get().getGrupa()
                .equals(comboBoxGroupFilter.getSelectionModel().getSelectedItem());



//        String idS=service.getIdStudent(this.comboBoxNumeFilter.getSelectionModel().getSelectedItem());
//        String idT=this.comboBoxLabFilter.getSelectionModel().getSelectedItem();

//        LocalDate date1 = this.datePickerFilter1.getValue(); // input from your date picker
//        Integer week1=service.getWeek(date1);
//        Integer weekUni1=service.getWeekUni(week1);
//
//        LocalDate date2 = this.datePickerFilter1.getValue(); // input from your date picker
//        Integer week2=service.getWeek(date2);
//        Integer weekUni2=service.getWeekUni(week2);

//        Predicate<NotaDTO> p4 = n->service.cautaNota(idS,idT).getDataCurenta().isEmpty() ||
//                this.datePickerFilter1.getValue().toString().isEmpty() ||
//                this.datePickerFilter2.getValue().toString().isEmpty() ||
//                Integer.parseInt(service.cautaNota(idS,idT).getDataCurenta()) >=weekUni1 &&
//                        Integer.parseInt(service.cautaNota(idS,idT).getDataCurenta()) <=weekUni2;


//        Predicate<NotaDTO> p1 = n->n.getIdTema()
//                .equals(comboBoxLabFilter.getSelectionModel().getSelectedItem());
//
//        Predicate<NotaDTO> p2 = n->comboBoxNumeFilter.getSelectionModel().getSelectedItem()
//                .contains(n.getNumeStudent());
//
//        Predicate<NotaDTO> p3 = n->service.cautaStudent(n.getIdStudent()).get().getGrupa()
//                .equals(comboBoxGroupFilter.getSelectionModel().getSelectedItem());

        model.setAll(service.listaNoteDTO().stream()
                .filter(p3.and(p1))
                .collect(Collectors.toList())
        );

    }
    @FXML
    private void handleClear(){
        model.setAll(service.listaNoteDTO());
    }

}
