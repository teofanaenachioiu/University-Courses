package view;

import domain.MessageTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MessageTaskView {

    private MessageTaskController ctrl;
    TextField txtfieldID = new TextField();
    TextField txtfieldDescriere = new TextField();
    TextField txtfieldFrom = new TextField();
    TextField txtfieldTo = new TextField();
    TextArea  txtareaMessage = new TextArea();

    public MessageTaskView(MessageTaskController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    private BorderPane borderPane;

    TableView<MessageTask> tableView=new TableView<>();


    private void initView() {

        borderPane=new BorderPane();
        //top AnchorPane
        borderPane.setTop(initTop());
//        //left
        borderPane.setLeft(initLeft());
//        //center
        borderPane.setCenter(initCenter());

    }


    private AnchorPane initTop()
    {
        AnchorPane topAnchorPane=new AnchorPane();
        ImageView img=new ImageView(new Image("logo.gif"));

        img.setFitHeight(100);
        img.setFitWidth(100);
        img.setPreserveRatio(true);

        topAnchorPane.getChildren().add(img);

        Label titleLabel=new Label("MessageTask Operations...");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,20d);
        AnchorPane.setLeftAnchor(titleLabel,100d);
        titleLabel.setFont(new Font(30));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return topAnchorPane;
    }

    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView=createMessageTaskTableView();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);
        AnchorPane.setRightAnchor(tableView,20d);
        AnchorPane.setTopAnchor(tableView,20d);
        AnchorPane.setBottomAnchor(tableView,20d);

        return  leftAnchorPane;
    }
    public AnchorPane initCenter()
    {
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane = createGridPane();
        AnchorPane.setRightAnchor(gridPane,20d);
        centerAnchorPane.getChildren().add(gridPane);

        HBox buttonZone = new HBox();
        Button addButton = new Button("Add");
        addButton.setOnAction(ctrl::handleAddMessage);
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        Button clearButton = new Button("Clear Fields");
        buttonZone.getChildren().addAll(addButton,deleteButton,updateButton,clearButton);
        AnchorPane.setBottomAnchor(buttonZone,5d);

        centerAnchorPane.getChildren().add(buttonZone);
        return centerAnchorPane;
    }

    private GridPane createGridPane()
    {
        GridPane gp = new GridPane();
        Label labelID = createLabel("ID");
        Label labelDescriere = createLabel("Descriere:");
        Label labelMesaj = createLabel("Mesaj:");
        Label labelFrom = createLabel("From:");
        Label labelTo = createLabel("To:");
        gp.add(labelID,0,0);
        gp.add(this.txtfieldID,1,0);
        gp.add(labelDescriere,0,1);
        gp.add(this.txtfieldDescriere,1,1);
        gp.add(labelFrom,0,2);
        gp.add(this.txtfieldFrom,1,2);
        gp.add(labelTo,0,3);
        gp.add(this.txtfieldTo,1,3);
        gp.add(labelMesaj,0,4);
        gp.add(this.txtareaMessage,1,4);
        gp.setHgap(5);
        gp.setVgap(5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(100);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(250);

        gp.getColumnConstraints().addAll(c1,c2);
        return gp;
    }


    private TableView<MessageTask> createMessageTaskTableView()
    {
        TableColumn<MessageTask,String> colDesc = new TableColumn<>("Descriere");
        TableColumn<MessageTask,String> colFrom = new TableColumn<>("From");
        TableColumn<MessageTask,String> colTo = new TableColumn<>("To");
        TableColumn<MessageTask,String> colDate = new TableColumn<>("Date");
        tableView.getColumns().addAll(colDesc,colFrom,colTo,colDate);
        colDesc.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("message"));
        colFrom.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("from"));
        colTo.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("to"));
        colDate.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("date"));
        tableView.setItems(ctrl.getModel());
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MessageTask>() {
            @Override
            public void changed(ObservableValue<? extends MessageTask> observable, MessageTask oldValue, MessageTask newValue)
            {
                ctrl.showMessageTaskDetails(newValue);
            }
        });
        return tableView;
    }


    public BorderPane getView(){ return borderPane;}

    //auxiliars methods

    private Label createLabel(String s){
        Label l=new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.RED);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

}
