package tasks.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import tasks.controller.TasksController;
import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;
import tasks.repository.RepositoryException;

/**
 * Created by grigo on 11/14/16.
 */
public class SortingTasksView {
    BorderPane pane;
    TextField taskIdText,descBox,nrElem;
    Button exeTask,canTask;
    ToggleGroup group;
    RadioButton asc, desc;
    ChoiceBox<SortingAlgorithm> algoCb;
    TasksController controller;
    public SortingTasksView(TasksController contr){
        this.controller=contr;
        initView();

    }

    private void initView() {
        pane=new BorderPane();
        pane.setRight(createTask());
        pane.setCenter(createTable());
    }

    public BorderPane getView(){

        return pane;
    }

    private TableView<SortingTask> table = new TableView<>();
    protected StackPane createTable(){
        StackPane pane=new StackPane();
        initTasksView();
        pane.getChildren().add(table);
        return pane;
    }

    private void initTasksView(){
        TableColumn<SortingTask, Integer> idCol = new TableColumn<>("Id");
        TableColumn<SortingTask, String> descCol = new TableColumn<>("Description");
        TableColumn<SortingTask, Integer> elemCol = new TableColumn<>("#elem");
        TableColumn<SortingTask, SortingOrder> orderCol = new TableColumn<>("Order");
        TableColumn<SortingTask, SortingAlgorithm> algCol = new TableColumn<>("Algorithm");

        table.getColumns().addAll(idCol,descCol,elemCol,orderCol,algCol);

        //stabilirea valorilor asociate unei celule
        idCol.setCellValueFactory(new PropertyValueFactory<SortingTask, Integer>("id")); //
        descCol.setCellValueFactory(new PropertyValueFactory<SortingTask, String>("desc"));
        elemCol.setCellValueFactory(new PropertyValueFactory<SortingTask, Integer>("nrElem"));
        orderCol.setCellValueFactory(new PropertyValueFactory<SortingTask, SortingOrder>("order"));
        algCol.setCellValueFactory(new PropertyValueFactory<SortingTask, SortingAlgorithm>("alg"));

        table.setItems(controller.getTasksModel());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Listen for selection changes and show the SortingTask details when changed.
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)->showSortingTaskDetails(newValue) );

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
            algoCb.getSelectionModel().select(value.getAlg());
        }
    }

    protected GridPane createTask(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        Text scenetitle = new Text("SortingTask");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        scenetitle.setFill(Color.BLUE);
        grid.add(scenetitle, 0, 0, 2, 1);
        //id
        Label taskID = new Label("Id:");
        grid.add(taskID, 0, 1);
        taskIdText = new TextField();
        grid.add(taskIdText, 1, 1);
        //description
        Label pw = new Label("Description:");
        grid.add(pw, 0, 2);
        descBox = new TextField();
        grid.add(descBox, 1, 2);
        //Sorting order
        group = new ToggleGroup();
        asc = new RadioButton("Ascending");
        asc.setToggleGroup(group);
        asc.setSelected(true);
        asc.setUserData(SortingOrder.Ascending);
        desc = new RadioButton("Descending");
        desc.setToggleGroup(group);
        desc.setUserData(SortingOrder.Descending);
        grid.add(asc,0,3);
        grid.add(desc,1,3);
        //Sorting algorithm

        Label algor=new Label("Algorithm:");
        grid.add(algor,0,4);
        algoCb=new ChoiceBox<>(FXCollections.observableArrayList(SortingAlgorithm.values()));
        algoCb.getSelectionModel().selectFirst();
        grid.add(algoCb,1,4);

        Label nrElemLabel=new Label("Nr elemente:");
        grid.add(nrElemLabel,0,5);
        nrElem=new TextField();
        grid.add(nrElem,1,5);

        Button addTask=new Button("Add");
        Button deleteTask=new Button("Delete");
        Button updateTask=new Button("Update");
        Button cancel=new Button("Cancel");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        addTask.setOnAction(x->addButton());
        deleteTask.setOnAction(x->handleDelete());
        updateTask.setOnAction(x->handleUpdateTask());
        cancel.setOnAction(x->cancelButton());
        hbBtn.getChildren().addAll(addTask, deleteTask,updateTask, cancel);
        grid.add(hbBtn,0,6,2,1);



        return grid;
    }

    private void handleUpdateTask(){
        System.out.println("S-a apasat update");
        int index=table.getSelectionModel().getSelectedIndex();
        if (index<0){
            showErrorMessage("Trebuie sa selectati un task!!!");
            return;
        }
        SortingTask oldTask=table.getSelectionModel().getSelectedItem();
        String id=taskIdText.getText();
        String desc=descBox.getText();
        SortingOrder orderV=(SortingOrder)group.getSelectedToggle().getUserData();
        SortingAlgorithm algo=algoCb.getSelectionModel().getSelectedItem();
        try{
            int idVal=Integer.parseInt(id);
            int nrElemVal=Integer.parseInt(nrElem.getText());
            controller.updateTask(oldTask, idVal, desc, orderV, algo, nrElemVal);

        }catch(NumberFormatException ex){
            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
        }catch (RepositoryException ex){
            showErrorMessage("Eroare la update: " + ex.getMessage());
        }


    }
    private void handleDelete() {
        int index=table.getSelectionModel().getSelectedIndex();
        if (index<0) {
            showErrorMessage("Eroare la stergere: trebuie sa selectati un task");
            return;
        }
        SortingTask task=table.getSelectionModel().getSelectedItem();
        controller.deleteTask(task);
    }

    private void addButton() {
        String id=taskIdText.getText();
        String desc=descBox.getText();
        SortingOrder orderV=(SortingOrder)group.getSelectedToggle().getUserData();
        SortingAlgorithm algo=algoCb.getSelectionModel().getSelectedItem();
        try{
            int idVal=Integer.parseInt(id);
            int nrElemVal=Integer.parseInt(nrElem.getText());
            controller.addSortingTask(idVal,desc,orderV,algo,nrElemVal);
            clearFields();

        }catch(NumberFormatException ex){
            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
        }catch (RepositoryException ex){
            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
        }
    }

    private void cancelButton(){
        table.getSelectionModel().clearSelection();
        clearFields();
    }

    private void clearFields(){
        taskIdText.setText("");
        descBox.setText("");
        nrElem.setText("");
        asc.setSelected(true);
        algoCb.getSelectionModel().selectFirst();
    }


    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

}
