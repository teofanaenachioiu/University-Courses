package sem8_FXML.controller;

import common.domain.MessageTask;

import common.domain.validator.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import common.service.MessageTaskService;


import java.time.LocalDateTime;


public class EditMessageTaskController {
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldDesc;
    @FXML
    private TextField textFieldFrom;
    @FXML
    private TextField textFieldTo;
    @FXML
    private TextArea textAreaMessage;
    @FXML
    private DatePicker datePickerDate;

    private MessageTaskService service;
    Stage dialogStage;
    MessageTask message;

    @FXML
    private void initialize() {
    }


    public void setService(MessageTaskService service,  Stage stage, MessageTask m) {
        this.service = service;
        this.dialogStage=stage;
        this.message=m;
        if (null != m) {
            setFields(m);
            textFieldId.setEditable(false);
        }
    }

    @FXML
    public void handleSave(){
        String id=textFieldId.getText();
        String desc=textFieldDesc.getText();
        String from=textFieldFrom.getText();
        String to=textFieldTo.getText();
        String message=textAreaMessage.getText();
        MessageTask m=new MessageTask(id,desc,message,from,to,LocalDateTime.now());
        if (null == this.message)
            saveMessage(m);
        else
            updateMessage(m);

    }

    private void updateMessage(MessageTask m)
    {
        try {
            MessageTask r= this.service.updateMessageTask(m);
            if (r==null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare mesaj","Mesajul a fost modificat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }


    private void saveMessage(MessageTask m)
    {
        try {
            MessageTask r= this.service.addMessageTaskTask(m);
            if (r==null)
                dialogStage.close();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Slavare mesaj","Mesajul a fost salvat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }

    }

    private void clearFields() {
        textFieldId.setText("");
        textFieldDesc.setText("");
        textFieldFrom.setText("");
        textFieldTo.setText("");
        textAreaMessage.setText("");
    }
    private void setFields(MessageTask s)
    {
        textFieldId.setText(s.getID());
        textFieldDesc.setText(s.getDescription());
        textFieldFrom.setText(s.getFrom());
        textFieldTo.setText(s.getTo());
        textAreaMessage.setText(s.getMessage());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
