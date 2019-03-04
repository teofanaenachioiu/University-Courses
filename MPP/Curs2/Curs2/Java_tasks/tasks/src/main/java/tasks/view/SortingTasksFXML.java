package tasks.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;
import tasks.model.TaskExecutionException;
import tasks.repository.RepositoryException;
import tasks.service.TaskService;
import tasks.utils.Observer;
import tasks.utils.SortingTaskEvent;
import tasks.utils.TaskEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by grigo on 11/22/16.
 */
public class SortingTasksFXML implements Initializable, Observer<SortingTaskEvent>{
    @FXML
    private TableView <SortingTask> table;

    @FXML private RadioButton asc, desc;

    @FXML private ChoiceBox<SortingAlgorithm> alg;
    @FXML private ToggleGroup orderGroup;

    @FXML private TextField taskIdText,descBox, nrElem;
    @FXML private TextArea execMessages;

    public SortingTasksFXML() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alg.setItems(FXCollections.observableArrayList(SortingAlgorithm.values()));
        alg.getSelectionModel().selectFirst();
        asc.setSelected(true);
        asc.setUserData(SortingOrder.Ascending);
        desc.setUserData(SortingOrder.Descending);
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)->showSortingTaskDetails(newValue) );
        execMessages.setEditable(false);
        exeTask.setDisable(true);
        cancelExec.setDisable(true);
       // progress.setProgress(0);
        progress.indeterminateProperty().not();

    }

    private void showSortingTaskDetails(SortingTask value) {
        if (value==null)
            clearFields();
        else{
            taskIdText.setText(""+value.getId());
            descBox.setText(value.getDesc());
            nrElem.setText(""+value.getNrElem());
            if (value.getOrder()==SortingOrder.Ascending){
                asc.setSelected(true);
            }else
                desc.setSelected(true);
            alg.getSelectionModel().select(value.getAlg());
            if (cancelExec.isDisabled()){
                exeTask.setDisable(false);
//                progress.setProgress(0);

            }
        }
    }
    private TaskService service;
    public void setTasksService(TaskService service){
       this.service=service;
        service.addObserver(this);
        service.addRunnerObserver(new RunnerObserver());
        initData();
    }

    private void initData() {
        table.getItems().clear();
        for (SortingTask task:service.getAll()) {
            table.getItems().add(task);
        }
    }

    @FXML
    private void addButton(ActionEvent ec) {
        String id=taskIdText.getText();
        String desc=descBox.getText();
        SortingOrder orderV=(SortingOrder)orderGroup.getSelectedToggle().getUserData();
        SortingAlgorithm algo=alg.getSelectionModel().getSelectedItem();
        try{
            int idVal=Integer.parseInt(id);
            int nrElemVal=Integer.parseInt(nrElem.getText());
            addSortingTask(idVal,desc,orderV,algo,nrElemVal);
            clearFields();

        }catch(NumberFormatException ex){
            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
        }catch (RepositoryException ex){
            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
        }
    }

    public void addSortingTask(int id, String desc, SortingOrder order, SortingAlgorithm alg, int nrElem){
        SortingTask task=new SortingTask(id,desc,alg,order,nrElem);
        service.addSortingTask(task);

    }

    @FXML private void deleteButton(ActionEvent ev){
        int index=table.getSelectionModel().getSelectedIndex();
        if (index<0) {
            showErrorMessage("Eroare la stergere: trebuie sa selectati un task");
            return;
        }
        SortingTask task=table.getSelectionModel().getSelectedItem();
        deleteTask(task);
    }
    public void deleteTask(SortingTask task){
        service.deleteSortingTask(task);
    }

    @Override
    public void update(SortingTaskEvent sortingTaskEvent) {
        switch (sortingTaskEvent.getType()){
            case ADD:{ table.getItems().add(sortingTaskEvent.getData()); break;}
            case DELETE:{table.getItems().remove(sortingTaskEvent.getData()); break;}
            case UPDATE:{ table.getItems().remove(sortingTaskEvent.getOldData());
                table.getItems().add(sortingTaskEvent.getData()); break;}
        }
    }

    @FXML public void updateButton(ActionEvent ev){
        int index=table.getSelectionModel().getSelectedIndex();
        if (index<0){
            showErrorMessage("Trebuie sa selectati un task!!!");
            return;
        }
        SortingTask oldTask=table.getSelectionModel().getSelectedItem();
        String id=taskIdText.getText();
        String desc=descBox.getText();
        SortingOrder orderV=(SortingOrder)orderGroup.getSelectedToggle().getUserData();
        SortingAlgorithm algo=alg.getSelectionModel().getSelectedItem();
        try{
            int idVal=Integer.parseInt(id);
            int nrElemVal=Integer.parseInt(nrElem.getText());
            updateTask(oldTask, idVal, desc, orderV, algo, nrElemVal);

        }catch(NumberFormatException ex){
            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
        }catch (RepositoryException ex){
            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
        }
    }
    public void updateTask(SortingTask oldTask, int idVal, String desc, SortingOrder orderV, SortingAlgorithm algo, int nrElemVal) {
        SortingTask newTask=new SortingTask(idVal,desc,algo,orderV,nrElemVal);
        service.updateSortingTask(oldTask,newTask);
    }

     void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    @FXML public void cancelButton(ActionEvent e){
        table.getSelectionModel().clearSelection();
        clearFields();
    }
    private void clearFields(){
        taskIdText.setText("");
        descBox.setText("");
        nrElem.setText("");
        asc.setSelected(true);
        alg.getSelectionModel().selectFirst();
        exeTask.setDisable(true);

        //cancelExec.setDisable(true);
    }

    private class RunnerObserver implements Observer<TaskEvent>{

        @Override
        public void update(TaskEvent taskEvent) {
            switch (taskEvent.getType()){
                case StartingTaskExecution:{appendMessage("Starting execution of :"+taskEvent.getTask()); break;}
                case TaskExecutionCompleted:{appendMessage("Completed execution of "+taskEvent.getTask()); break;}
            }
        }
    }
    @FXML void  handleAddTaskRunner(ActionEvent e){
        TextInputDialog inputControl=new TextInputDialog();
        inputControl.setContentText("Introduceti un task id:");
        inputControl.setTitle("Add task to Runner");
        inputControl.setHeaderText("");
        Optional<String> result=inputControl.showAndWait();
        if (result.isPresent()){
            try{
                int idV=Integer.parseInt(result.get());
                service.addTaskToRunner(idV);
                appendMessage("Task-ul cu  "+idV+" adaugat la TaskRunner");
            }catch (NumberFormatException ex){
                showErrorMessage("Trebuie sa introduceti un id valid"+ex.getMessage());
            }catch (RepositoryException ex){
                showErrorMessage(ex.getMessage());
            }
        }
    }

    @FXML void  handleExecuteOne(ActionEvent e){
        service.executeOneTask();
    }

    private Task<Void> executeAllTask;
    @FXML void handleExecuteALL(ActionEvent e){
        executeAllTask=new Task<Void>(){
            @Override public Void call(){
                service.executeAll();
                return null;
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                service.cancelRunner();
            }
        };
       // service.executeAll();
        Thread th=new Thread(executeAllTask);
        th.setDaemon(true);
        th.start();
    }
    private void appendMessage(String text){
       // execMessages.appendText(text+"\n");
       Platform.runLater(new Runnable() {
            @Override public void run() {
                execMessages.appendText(text+"\n");
            }
        });

    }

    @FXML Button exeTask;
    @FXML Button cancelExec;

    private Task<Void> runningTask;
    @FXML void execButton(ActionEvent e){
        int index=table.getSelectionModel().getSelectedIndex();
        if (index<0){
            showErrorMessage("Trebuie sa selectati un task!!!");
            return;
        }
        SortingTask oldTask=table.getSelectionModel().getSelectedItem();
        //oldTask.execute();

        runningTask = new Task<Void>() {
            @Override public Void call() {

                try{
                    oldTask.execute();
                    updateMessage("Done");
                    updateProgress(1,1);
                }catch(TaskExecutionException ex){
                    System.out.println("Execution task cancelled");
                     updateMessage("Cancelled");


                    updateProgress(0,1);
                    System.out.println("Cancelled before unbind ");
                   // progress.progressProperty().unbind();

                    // progress.progressProperty().unbind();
                    if (table.getSelectionModel().getSelectedIndex()>=0)
                        exeTask.setDisable(false);
                    cancelExec.setDisable(true);
                }
                return null;
            }

            @Override protected void succeeded() {
                super.succeeded();
                updateMessage("Done!");
                progress.progressProperty().unbind();
                if (table.getSelectionModel().getSelectedIndex()>=0)
                    exeTask.setDisable(false);
                cancelExec.setDisable(true);
            }


        };
       // progress = new ProgressBar();
        progress.progressProperty().bind(runningTask.progressProperty());
        execLabel.textProperty().bind(runningTask.messageProperty());


        cancelExec.setDisable(false);
        exeTask.setDisable(true);

        Thread th=new Thread(runningTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML void cancelExecButton(ActionEvent e){
        runningTask.cancel();
        if (table.getSelectionModel().getSelectedIndex()>=0)
            exeTask.setDisable(false);
        cancelExec.setDisable(true);

    }

    @FXML ProgressBar progress;
    @FXML Label execLabel;

    public void close(){
      System.out.println("Ctrl closing");
        if((runningTask!=null)&&(runningTask.isRunning()))
        {
            System.out.println("Task still running ...");
            runningTask.cancel();

        }
        if ((executeAllTask!=null)&&(executeAllTask.isRunning())) {
            System.out.println("Execute all still running ..");
            executeAllTask.cancel();
           // service.close();
        }
    }
}
