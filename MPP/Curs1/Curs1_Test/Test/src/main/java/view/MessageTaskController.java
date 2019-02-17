package view;

import domain.MessageTask;

import domain.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import service.TaskServiceManager;

import utils.ListEvent;
import utils.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MessageTaskController implements Observer<MessageTask>{

    private TaskServiceManager service;
    private ObservableList<MessageTask> model;

    private MessageTaskView view;

    public MessageTaskController(TaskServiceManager service) {
        this.service = service;
        model= FXCollections.observableArrayList(service.getAllMessageTasks());
    }

    @Override
    public void notifyEvent(ListEvent<MessageTask> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(),false)
                .collect(Collectors.toList()));  // important
    }

    public MessageTaskView getView() {
        return view;
    }

    public void setView(MessageTaskView view) {
        this.view = view;
    }

    public ObservableList<MessageTask> getModel() {
        return model;
    }

    public void handleAddMessage(ActionEvent actionEvent)
    {
        MessageTask toAdd = extractMessage();
        try
        {
            MessageTask returned = service.saveMessageTask(toAdd);
            if (returned == null)
            {
                showMessage(Alert.AlertType.INFORMATION,"Obiect adaugat","Obiectul a fost adaugat.");
            }
            else
            {
                showErrorMessage("Exista deja un mesaj cu ID-ul "+returned.getId());
            }
        }
        catch (ValidationException e)
        {
            showErrorMessage("Valdiation exception: "+e.getMessage());
            e.printStackTrace();//TODO remove
        }
    }

    public void handleUpdateMessage(ActionEvent actionEvent) {
    }

    public void handleClearFields(ActionEvent actionEvent) {
    }

    public void handleDeleteMessage(ActionEvent actionEvent) {
    }


    //other methods
    private MessageTask extractMessage()
    {
        String id = view.txtfieldID.getText();
        String from = view.txtfieldFrom.getText();
        String to = view.txtfieldTo.getText();
        String desc = view.txtfieldDescriere.getText();
        String msg = view.txtareaMessage.getText();
        return new MessageTask(id,desc,from,to,msg,LocalDateTime.now());
    }
    static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }


    public void showMessageTaskDetails(MessageTask newValue)
    {
        if (newValue!=null)
        {
            view.txtareaMessage.setText(newValue.getMessage());
            view.txtfieldDescriere.setText(newValue.getDesc());
            view.txtfieldFrom.setText(newValue.getFrom());
            view.txtfieldTo.setText(newValue.getTo());
            view.txtfieldID.setText(newValue.getId());
        }
        else
        {
            view.txtfieldDescriere.setText("");
            view.txtfieldFrom.setText("");
            view.txtfieldTo.setText("");
            view.txtareaMessage.setText("");
            view.txtfieldID.setText("");
        }
    }
}
