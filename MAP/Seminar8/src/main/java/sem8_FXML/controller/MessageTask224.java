package sem8_FXML.controller;

import common.domain.MessageTask;
import common.service.MessageTaskService;
import common.utils.observer.Observer;
import common.utils.taskChangeEvent.MessageTaskChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageTask224 implements Observer<MessageTaskChangeEvent> {

    private MessageTaskService service;
    private ObservableList<MessageTask> model = FXCollections.observableArrayList();

    @FXML
    private TableColumn<MessageTask, String> tableColumnFrom;
    @FXML
    private TableColumn<MessageTask,String> tableColumnTo;
    @FXML
    private TableColumn<MessageTask,String> tableColumnDesc;
    @FXML
    private TableColumn<MessageTask,String> tableColumnData;
    @FXML
    private TableView<MessageTask> tableView;

    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
        model.setAll(StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    @FXML
    public void initialize() {
        tableColumnDesc.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("description"));
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("from"));
        tableColumnTo.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("to"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("date"));

        tableView.setItems(model);

       // txtSubject.textProperty().addListener(x -> handleFilter());
     //   txtTo.textProperty().addListener(x -> handleFilter());
     //   txtFrom.textProperty().addListener(x -> handleFilter());


//        comboBoxFilter.setItems(
//                FXCollections.observableArrayList("Subject","From","To","Date"));

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

    public void setMessageTaskService(MessageTaskService messageTaskService) {
        this.service = messageTaskService;
        messageTaskService.addObserver(this);
        initModel();
    }
    private void initModel() {
        List<MessageTask> list = StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);
    }

}
