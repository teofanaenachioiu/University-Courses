package sem8_FXML.controller;

import common.domain.MessageTask;
import common.service.MessageTaskService;
import common.utils.observer.Observer;
import common.utils.taskChangeEvent.MessageTaskChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sem8_FXML.samples.AutoCompleteComboBoxListener;


import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageTaskController implements Observer<MessageTaskChangeEvent> {

    private MessageTaskService service;
    private ObservableList<MessageTask> model = FXCollections.observableArrayList();

    @FXML
    private TableView<MessageTask> tableView;
    @FXML
    private TableColumn<MessageTask, String> tableColumnDesc;
    @FXML
    private TableColumn<MessageTask, String> tableColumnFrom;
    @FXML
    private TableColumn<MessageTask, String> tableColumnTo;
    @FXML
    private TableColumn<MessageTask, String> tableColumnData;


    @FXML
    public void initialize() {
        tableColumnDesc.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("description"));
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("from"));
        tableColumnTo.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("to"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("date"));

        tableView.setItems(model);

        txtSubject.textProperty().addListener(x -> handleFilter());
        txtTo.textProperty().addListener(x -> handleFilter());
        txtFrom.textProperty().addListener(x -> handleFilter());


//        comboBoxFilter.setItems(
//                FXCollections.observableArrayList("Subject","From","To","Date"));

    }

    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
        model.setAll(StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public void setMessageTaskService(MessageTaskService messageTaskService) {
        this.service = messageTaskService;
        messageTaskService.addObserver(this);
        initModel();

        initComboBoxSubject();
    }



    private void initModel() {
        List<MessageTask> list = StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);
    }

    @FXML
    public void handleAddMessage(ActionEvent ev) {
        showMessageTaskEditDialog(null);
    }

    @FXML
    public void handleDeleteMessage(ActionEvent ev) {
        MessageTask selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            MessageTask deleted = service.deleteMessageTask(selected);
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");
    }

    @FXML
    public void handleUpdateMessage(ActionEvent ev) {
        MessageTask selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showMessageTaskEditDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
    }


    public void showMessageTaskEditDialog(MessageTask messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editMessageTaskView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditMessageTaskController editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage, messageTask);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    TextField txtFrom;

    @FXML
    TextField txtTo;

    @FXML
    TextField txtSubject;

    @FXML
    ComboBox<String> comboBoxFilter;

    @FXML
    private ComboBox<String> comboBoxSubject;

    @FXML
    private ComboBox<String> comboBoxFrom;

    @FXML
    private ComboBox<String> comboBoxTo;


    public void handleFilter() {
        String subject = txtSubject.getText();
        String from = txtFrom.getText();
        String to = txtTo.getText();
        Predicate<MessageTask> bySubjectPredicate = m -> m.getDescription().contains(subject);
        Predicate<MessageTask> fromPredicate = m -> m.getFrom().contains(from);
        Predicate<MessageTask> toPredicate = m -> m.getTo().contains(to);

        model.setAll(StreamSupport.stream(service.getAll().spliterator(), false)
                .filter(bySubjectPredicate.and(fromPredicate).and(toPredicate))
                .collect(Collectors.toList()));
    }

    private void initComboBoxSubject() {
        List<String> list=StreamSupport.stream(service.getAll().spliterator(), false)
                .map(m->m.getDescription())
                .collect(Collectors.toList());
        comboBoxSubject.getItems().addAll(list);
        new AutoCompleteComboBoxListener<String>(comboBoxSubject);
    }


    //needs enter
    public void handleFilterBySubjectOnAction(ActionEvent actionEvent) {
        handleFilter();
    }


    public void handleFilterFrom(KeyEvent keyEvent) {
        handleFilter();
    }

    public void handleFilterBySubject(KeyEvent keyEvent) {
        handleFilter();
    }

    public void handleFilterTo(KeyEvent keyEvent) {
        handleFilter();
    }

    public void handleFilterBySubject2(ActionEvent actionEvent) {

    }
}
