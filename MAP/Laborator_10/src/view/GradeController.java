package view;

import domain.Nota;
import domain.NotaDTO;
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
import service.Service;
import utils.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController implements Observer<NotaChangeEvent> {
    public AverageController averageController;
    public ExamController examController;
    public BorderPane GradeBorderPane;
    public Button imageButton;
    public Service service;
    private ObservableList<NotaDTO> model;

    private Stage primaryStage;
    private Scene mainScene;

    private FXMLLoader loaderAvg,loaderExam;
    private Scene sceneAvg,sceneExam;
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

    @FXML
    private Button buttonAverage;

    public GradeController() {
        model= FXCollections.observableArrayList();
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    private void initComboBox(){
        List<String> lista= StreamSupport.stream(service.listaStudenti().spliterator(),false).
                map(student->student.getNume()+" (ID:"+student.getID()+")").collect(Collectors.toList());
        ObservableList<String> data = FXCollections.observableArrayList(lista);

        this.comboBoxNumeStudent.setItems(data);
        this.comboBoxNumeFilter.setItems(data);
        new AutoCompleteComboBoxListener<>(this.comboBoxNumeStudent);
        new AutoCompleteComboBoxListener<>(this.comboBoxNumeFilter);

        ObservableList<String> dataFilter = FXCollections.observableArrayList(
                "221", "222", "223", "224", "225", "226", "227");
        this.comboBoxGroupFilter.setItems(dataFilter);
    }

    private void setVisibleAdd(boolean apare){
        this.labelLab.setVisible(apare);
        this.labelGrade.setVisible(apare);
        this.labelStudent.setVisible(apare);
        this.labelFeedback.setVisible(apare);
        this.comboBoxLab.setVisible(apare);
        this.textFieldGrade.setVisible(apare);
        this.comboBoxNumeStudent.setVisible(apare);
        this.textAreaFeedback.setVisible(apare);
        this.addButton.setVisible(apare);
        this.checkBoxMotivation.setVisible(apare);
    }

    private void setVisibleFilter(boolean apare){
        this.labelLabFilter.setVisible(apare);
        this.labelStudentFilter.setVisible(apare);
        this.labelGroupFilter.setVisible(apare);
        this.labelTimeFilter.setVisible(apare);
        this.comboBoxLabFilter.setVisible(apare);
        this.comboBoxNumeFilter.setVisible(apare);
        this.comboBoxGroupFilter.setVisible(apare);
        this.datePickerFilter1.setVisible(apare);
        this.datePickerFilter2.setVisible(apare);
        this.buttonClear.setVisible(apare);
    }

    @FXML
    private void initialize(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("numeStudent"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<>("idTema"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        tableView.setItems(model);

        ObservableList<String> data = FXCollections.observableArrayList("1", "2", "3","4","5","6","7","8","9","10");
        this.comboBoxLab.setItems(data);
        this.comboBoxLabFilter.setItems(data);

        setVisibleAdd(false);
        setVisibleFilter(false);

        this.comboBoxNumeFilter.valueProperty().addListener(o->handleFilter());
        this.comboBoxLabFilter.valueProperty().addListener(o->handleFilter());
        this.comboBoxGroupFilter.valueProperty().addListener(o->handleFilter());
        this.datePickerFilter1.valueProperty().addListener(o->handleFilter());
        this.datePickerFilter2.valueProperty().addListener(o->handleFilter());
    }

    @FXML
    private void handleAddGradeButton(){
        if(this.labelLab.isVisible()) {
            this.setVisibleAdd(false);
            this.setVisibleFilter(false);
        }
        else {
            setVisibleAdd(true);
            setVisibleFilter(false);
        }
    }

    @FXML
    private void handleFilterButton(){
        if(labelLabFilter.isVisible()){
            setVisibleAdd(false);
            setVisibleFilter(false);
        }
        else{
            setVisibleAdd(false);
            setVisibleFilter(true);
        }
    }

    private Nota extractNota(){
        String idLab=this.comboBoxLab.getValue();
        String numeStudent=this.comboBoxNumeStudent.getValue();
        if(numeStudent==null) return null;
        String nota=this.textFieldGrade.getText();
        if(nota==null) return null;
        String idStudent=service.getIdStudent(numeStudent);
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

        int dif=assignment-lab;

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
                gradeController.init(stage,nota,motivat,feedback,this);

                showDetails();
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
        this.comboBoxLab.getSelectionModel().select(nrAssignment-1);

        initComboBox();

        // Medii suntdenti
        loaderAvg=new FXMLLoader();
        loaderAvg.setLocation(getClass().getResource("/view/AverageView.fxml"));
        try {
            sceneAvg=new Scene(loaderAvg.load(),400,400);
            averageController=loaderAvg.getController();
            averageController.init(service);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Studentii care pot intra in examen
        loaderExam=new FXMLLoader();
        loaderExam.setLocation(getClass().getResource("/view/ExamView.fxml"));
        try {
            sceneExam=new Scene(loaderExam.load(),400,400);
            examController=loaderExam.getController();
            examController.init(service);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        //Trebuie OBSERVER!!!
        List<String> lista= StreamSupport.stream(service.listaStudenti().spliterator(),false).
                map(student->student.getNume()+" (ID:"+student.getID()+")").collect(Collectors.toList());
        ObservableList<String> data = FXCollections.observableArrayList(lista);
        this.comboBoxNumeStudent.setItems(data);
    }

    private Integer getDate1(){
        if(this.datePickerFilter1.getValue()!=null){
            LocalDate date1 = this.datePickerFilter1.getValue(); // input from your date picker
            Integer week1=service.getWeek(date1);
            return service.getWeekUni(week1);
        }
        return 0;
    }

    private Integer getDate2(){
        if(this.datePickerFilter2.getValue()!=null){
            LocalDate date2 = this.datePickerFilter2.getValue(); // input from your date picker
            Integer week2=service.getWeek(date2);
            return service.getWeekUni(week2);
        }
        return 15;
    }

    @FXML
    private void handleFilter() {
        Predicate<NotaDTO> p4 = n->
                service.cautaNota(n.getIdStudent(),n.getIdTema())
                .getDataCurenta().isEmpty() ||
                this.datePickerFilter1.getValue()==null ||
                this.datePickerFilter2.getValue()==null ||
                Integer.parseInt(service.cautaNota(n.getIdStudent(),n.getIdTema()).getDataCurenta()) >=getDate1() &&
                        Integer.parseInt(service.cautaNota(n.getIdStudent(),n.getIdTema()).getDataCurenta()) <=getDate2();

        Predicate<NotaDTO> p1 = n->
                this.comboBoxLabFilter.getSelectionModel().getSelectedItem()==null ||
                n.getIdTema()
                .contains(this.comboBoxLabFilter.getSelectionModel().getSelectedItem());

        Predicate<NotaDTO> p2 = n->
                this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()==null ||
                n.getNumeStudent().contains(this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()) ||
                this.comboBoxNumeFilter.getSelectionModel().getSelectedItem()
                .contains(n.getNumeStudent());

        Predicate<NotaDTO> p3 = n->
                this.comboBoxGroupFilter.getSelectionModel().getSelectedItem()==null ||
                this.service.cautaStudent(n.getIdStudent()).get().getGrupa()
                .contains(this.comboBoxGroupFilter.getSelectionModel().getSelectedItem());

        model.setAll(service.listaNoteDTO().stream()
                .filter(p4.and(p3).and(p2).and(p1))
                .collect(Collectors.toList()));
    }

    @FXML
    private void handleClear(){
        model.setAll(service.listaNoteDTO());
        this.comboBoxLabFilter.setValue(null);
        this.comboBoxNumeFilter.setValue(null);
        this.comboBoxGroupFilter.setValue(null);
        this.datePickerFilter2.setValue(null);
        this.datePickerFilter1.setValue(null);
    }

    @FXML
    private void handleMedieStudenti(){
        averageController.update(new NotaChangeEvent(ChangeEventType.ADD));
        Stage stage=new Stage();
        stage.setTitle("Average of grades");
        stage.setScene(sceneAvg);
        stage.show();
    }

    @FXML
    private void handlePromovat(){
        examController.update(new NotaChangeEvent(ChangeEventType.ADD));
        Stage stage=new Stage();
        stage.setTitle("Students");
        stage.setScene(sceneExam);
        stage.show();

    }
}
