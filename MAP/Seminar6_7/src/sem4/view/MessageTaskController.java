package sem4.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import sem4.domain.MessageTask;
import sem4.domain.validator.ValidationException;
import sem4.service.MessageTaskService;
import sem4.utils.MessageTaskChangeEvent;
import sem4.utils.Observer;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MessageTaskController implements Observer<MessageTaskChangeEvent> {

    private MessageTaskService service;
    private ObservableList<MessageTask> model;

    private MessageTaskView view;

    public MessageTaskController(MessageTaskService service) {
        this.service = service;
        List<MessageTask> list=StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model= FXCollections.observableArrayList(list);
    }


    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
        model.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .collect(Collectors.toList()));
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

    public void showMessageTaskDetails(MessageTask value) {
        if (value==null)
        {
            view.textFieldId.setText("");
            view.textFieldDesc.setText("");
            view.textFieldFrom.setText("");
            view.textFieldTo.setText("");
            view.textFieldMessage.setText("");
            view.textAreaMessage.setText("");

        }
        else
        {
            view.textFieldDesc.setText(value.getDescription());
            view.textFieldFrom.setText(value.getFrom());
            view.textFieldTo.setText(value.getTo());
            view.textFieldId.setText(value.getID());
            view.textAreaMessage.setText(value.getMessage());
        }
    }

    public void handleAddMessage(ActionEvent actionEvent) {
        MessageTask s= extractMessage();
        try {
            MessageTask  saved=service.addMessageTaskTask(s);
            if (saved==null) {
                showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Mesajul a fost adaugat!");
                showMessageTaskDetails(null);
            }
            else
                showErrorMessage("Exista deja un mesaj cu acest id!");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public MessageTask extractMessage(){
        String id=view.textFieldId.getText();
        String from=view.textFieldFrom.getText();
        String to=view.textFieldTo.getText();
        String message=view.textAreaMessage.getText();
        String desc=view.textFieldDesc.getText();
        MessageTask m=new MessageTask(id,desc, message,from,to, LocalDateTime.now());
        return m;
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

    public void handleClearFields(ActionEvent actionEvent) {
        //MessageTask m=new MessageTask("345","test","anamsnma","ssas" ,"me3",LocalDateTime.now());
        showMessageTaskDetails(null);
        //showMessageTaskDetails(m);
        view.textFieldId.setEditable(true);
    }


}
