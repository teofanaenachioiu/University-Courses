package view;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import repository.ValidationException;
import service.Service;
import utils.*;
import utils.Observable;
import utils.Observer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentController implements Observer<StudentChangeEvent>, Observable<ControllerChangeEvent> {
    private Service service;
    private ObservableList<Student> model;

    private StudentView view;

    private Stage primaryStage;
    private Scene mainScene;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    public StudentController(Service service) {
        setService(service);
        List<Student> list= StreamSupport.stream(service.listaStudenti().spliterator(), false)
                .collect(Collectors.toList());
        model= FXCollections.observableArrayList(list);
    }
    public void setService(Service service){
        this.service = service;
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        model.setAll(StreamSupport.stream(service.listaStudenti().spliterator(),false)
                .collect(Collectors.toList()));
        view.autoCompleteTextField.setEntries(new TreeSet<String>(listaNumeStudenti()));
    }

    public StudentView getView() {
        return view;
    }

    public void setView(StudentView view) {
        this.view = view;
    }

    public void handleAddStudent(ActionEvent actionEvent) {
        Student s=extractMessage();
        try {
            Optional<Student>  saved=service.adaugaStudent(s.getID(),s.getNume(),s.getGrupa(),s.getEmail(),s.getIndrumatorLab());
            if (!saved.isPresent()) {
                showStudentDetails(null);
                update(new StudentChangeEvent(ChangeEventType.ADD,s));
                showMessage(Alert.AlertType.INFORMATION, "Adaugare informatii", "Studentul a fost adaugat!");
            }
            else
                showErrorMessage("Exista deja un student cu acest id!");
        } catch (ValidationException e) {
            showErrorMessage("Date invalide!");
        }
    }

    public ObservableList<Student> getModel() {
        return model;
    }

    public ObservableList<Student> getFilterTeacher(String cond) {
        List<Student> listFilter= StreamSupport.stream(service.byTeacher(cond).spliterator(), false)
                .collect(Collectors.toList());
        ObservableList<Student> modelFilter= FXCollections.observableArrayList(listFilter);
        return modelFilter;
    }

    public ObservableList<Student> getFilterGroup(String cond) {
        List<Student> listFilter= StreamSupport.stream(service.byGroupe(cond).spliterator(), false)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(listFilter);
    }

    public void showStudentDetails(Student value) {
        if (value==null)
        {
            view.textFieldId.setText("");
            view.textFieldNume.setText("");
            view.textFieldGrupa.setText("");
            view.textFieldEmail.setText("");
            view.textFieldProf.setText("");

        }
        else
        {
            view.textFieldNume.setText(value.getNume());
            view.textFieldGrupa.setText(value.getGrupa());
            view.textFieldEmail.setText(value.getEmail());
            view.textFieldId.setText(value.getID());
            view.textFieldProf.setText(value.getIndrumatorLab());
        }
    }

    private Student extractMessage(){
        String id=view.textFieldId.getText();
        String nume=view.textFieldNume.getText();
        String grupa=view.textFieldGrupa.getText();
        String email=view.textFieldEmail.getText();
        String prof=view.textFieldProf.getText();
        return new Student(id,nume, grupa,email,prof);
    }

    private static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    public void handleUpdateStudent(Student student) {
        try {
            boolean updated = service.actualizareStudent(student.getID(), student.getNume(), student.getGrupa(), student.getEmail(), student.getIndrumatorLab());

            update(new StudentChangeEvent(ChangeEventType.ADD, student));
            if (updated) {
                update(new StudentChangeEvent(ChangeEventType.UPDATE, student));
                showMessage(Alert.AlertType.INFORMATION, "Date actualizate cu succes", "Informatiile a fost actualizate!");
            } else {
                showMessage(Alert.AlertType.WARNING, "Actualizare informatii", "Datele nu au fost modificate!");
            }
        }catch (ValidationException e){
            showErrorMessage("Date invalide!");
        }
    }

    public void handleUpdateStudentButton(ActionEvent actionEvent) {
        Student s=extractMessage();
        try {
            boolean  updated=service.actualizareStudent(s.getID(),s.getNume(),s.getGrupa(),s.getEmail(),s.getIndrumatorLab());
            if (updated) {
                showStudentDetails(null);
                update(new StudentChangeEvent(ChangeEventType.UPDATE,s));
                showMessage(Alert.AlertType.INFORMATION, "Date actualizate cu succes", "Informatiile a fost actualizate!");
            }
            else{
                showMessage(Alert.AlertType.WARNING,"Actualizare informatii","Datele nu au fost modificate!");
            }
        } catch (ValidationException e) {
            showErrorMessage("Date invalide!");
        }
    }

    public void handleDeleteStudentButton(ActionEvent actionEvent) {
        Student s=extractMessage();
        Optional<Student> rez=service.stergeStudent(s.getID());
        if(!rez.isPresent()) {
            showErrorMessage("Date invalide!");
        }
        else {
            update(new StudentChangeEvent(ChangeEventType.DELETE,s));
            showStudentDetails(null);
            showMessage(Alert.AlertType.INFORMATION,"Stergere student","Datele studentului au fost sterse!");
        }
    }

    public void handleClearStudentButton(ActionEvent actionEvent) {
        service.stergeStudenti();
        model.setAll(StreamSupport.stream(service.listaStudenti().spliterator(),false)
                    .collect(Collectors.toList()));
        showStudentDetails(null);
        showMessage(Alert.AlertType.INFORMATION,"Stergere studenti","Toate datele au fost sterse!");
        }

    public Collection<? extends String> listaNumeStudenti() {
        return service.studentList().values();
    }


    public void handleSearchButton(ActionEvent actionEvent) {
        Student student=service.getStudent(view.autoCompleteTextField.getText());
        showStudentDetails(student);
        view.autoCompleteTextField.setText("");
    }


    public void handleSearchField(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER) {
            Student student=service.getStudent(view.autoCompleteTextField.getText());
            showStudentDetails(student);
            view.autoCompleteTextField.setText("");
        }
    }

    public void handleComboBox(Event event) {
        if(view.comboBox.getValue().toString().equals("(no filter)")) {
            view.tableView.setItems(getModel());
            view.filterLabel.setVisible(false);
            view.textInput.setVisible(false);
            view.buttonApply.setVisible(false);
            view.textInput.setText("");
        }
        if(view.comboBox.getValue().toString().equals("by teacher")|| view.comboBox.getValue().toString().equals("by group")) {
            view.filterLabel.setVisible(true);
            view.textInput.setVisible(true);
            view.buttonApply.setVisible(true);
            view.textInput.setText("");
        }
    }

    public void handleComboBoxKey(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(view.comboBox.getValue().toString().equals("by teacher")) {
                view.tableView.setItems(getFilterTeacher(view.textInput.getText()));
                view.textInput.setText("");
            }
            if(view.comboBox.getValue().toString().equals("by group")) {
                view.tableView.setItems(getFilterGroup(view.textInput.getText()));
                view.textInput.setText("");
            }
        }
    }

    public void handleApplyButton(ActionEvent actionEvent) {
        if(view.comboBox.getValue().toString().equals("by teacher")) {
            view.tableView.setItems(getFilterTeacher(view.textInput.getText()));
            view.textInput.setText("");
        }
        if(view.comboBox.getValue().toString().equals("by group")) {
            view.tableView.setItems(getFilterGroup(view.textInput.getText()));
            view.textInput.setText("");
        }
    }

    public void handleEditColumnNume(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
        Student student=view.tableView.getSelectionModel().getSelectedItem();
        student.setNume(studentStringCellEditEvent.getNewValue());
        view.textFieldNume.setText(student.getNume());

        handleUpdateStudent(student);
    }

    public void handleEditColumnGrupa(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
        Student student=view.tableView.getSelectionModel().getSelectedItem();
        student.setGrupa(studentStringCellEditEvent.getNewValue());
        view.textFieldGrupa.setText(student.getGrupa());

        handleUpdateStudent(student);
    }

    public void handleEditColumnEmail(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
        Student student=view.tableView.getSelectionModel().getSelectedItem();
        student.setEmail(studentStringCellEditEvent.getNewValue());
        view.textFieldEmail.setText(student.getEmail());
        handleUpdateStudent(student);
    }

    public void handleEditColumnProf(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
        Student student=view.tableView.getSelectionModel().getSelectedItem();
        student.setIndrumatorLab(studentStringCellEditEvent.getNewValue());
        view.textFieldProf.setText(student.getIndrumatorLab());
        handleUpdateStudent(student);
    }

    public void handleExit(ActionEvent actionEvent) {
        this.primaryStage.setScene(mainScene);
    }

    private List<Observer<ControllerChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<ControllerChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ControllerChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ControllerChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
