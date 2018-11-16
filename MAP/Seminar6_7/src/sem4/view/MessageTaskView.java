package sem4.view;


import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sem4.domain.MessageTask;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class MessageTaskView {

    private MessageTaskController ctrl;

    public MessageTaskView(MessageTaskController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    private BorderPane borderPane;
    TableView<MessageTask> tableView=new TableView<>();


    TextField textFieldId=new TextField();
    TextField textFieldDesc=new TextField();
    TextField textFieldFrom=new TextField();
    TextField textFieldTo=new TextField();
    TextField textFieldMessage=new TextField();
    TextArea textAreaMessage=new TextArea();
    TextField textFieldDate=new TextField();


    private void initView() {
        borderPane=new BorderPane();
        //top AnchorPane
        borderPane.setTop(initTop());
        //left
        borderPane.setLeft(initLeft());
        //center
        borderPane.setCenter(initCenter());

        Label copyright=new Label("@........");
        AnchorPane a=new AnchorPane(copyright);
        AnchorPane.setBottomAnchor(copyright,50d);
        borderPane.setBottom(a);
    }

    private AnchorPane initTop()
    {
        AnchorPane topAnchorPane=new AnchorPane();

        ImageView img=new ImageView(
                new Image(new File("./src/sem4/logo.gif").toURI().toString()));

        img.setFitHeight(100);
        img.setFitWidth(100);
        img.setPreserveRatio(true);

        topAnchorPane.getChildren().add(img);
        AnchorPane.setLeftAnchor(img,5d);


        Label titleLabel=new Label("MessageTask Operations...");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,20d);
        AnchorPane.setRightAnchor(titleLabel,50d);
        titleLabel.setFont(new Font(30));
        return topAnchorPane;
    }

    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane=createGridPane();
        //anchor the gridpane
        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,20d);
        //AnchorPane.setTopAnchor(gridPane,20d);


        HBox buttonsGroups=createButtons();
        //anchor the buttons
        AnchorPane.setBottomAnchor(buttonsGroups,50d);
        AnchorPane.setLeftAnchor(buttonsGroups,20d);
        // buttonsGroups.setPadding(new Insets(30));
        centerAnchorPane.getChildren().add(buttonsGroups);
        return centerAnchorPane;
    }



    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView=createMessageTaskTable();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);
        //AnchorPane.setBottomAnchor(tableView,100d);
        return  leftAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();

        gridPaneMessageDetails.setHgap(5);
        gridPaneMessageDetails.setVgap(5);

        Label labelId=createLabel("Id:");
        Label labelDesc=createLabel("Subject:");
        Label labelFrom=createLabel("From");
        Label labelTo=createLabel("To:");
        Label labelDate=createLabel("Date:");
        Label labelMessage=createLabel("Message:");

        gridPaneMessageDetails.add(labelId,0,0);
        gridPaneMessageDetails.add(labelDesc,0,1);
        gridPaneMessageDetails.add(labelFrom,0,2);
        gridPaneMessageDetails.add(labelTo,0,3);
        //gridPaneMessageDetails.add(labelDate,0,4);
        gridPaneMessageDetails.add(labelMessage,0,4);

        gridPaneMessageDetails.add(textFieldId,1,0);
        gridPaneMessageDetails.add(textFieldDesc,1,1);
        gridPaneMessageDetails.add(textFieldFrom,1,2);
        gridPaneMessageDetails.add(textFieldTo,1,3);
        gridPaneMessageDetails.add(textAreaMessage,1,4);
        //gridPaneMessageDetails.add(textFieldDate,1,3);

        //gridPaneMessageDetails.addRow(5,new Label("dssndnmsndmnsmnd"));

        ColumnConstraints c1=new ColumnConstraints();
        c1.setPrefWidth(60d);

        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(250d);

        gridPaneMessageDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneMessageDetails;
    }

    public HBox createButtons(){
        //init HBox Button
        Button buttonAdd=new Button("Add");
        Button buttonUpdate=new Button("Update");
        Button buttonDelete=new Button("Delete");
        Button buttonClear = new Button("ClearAll");

        HBox hb=new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);
        buttonAdd.setOnAction(ctrl::handleAddMessage);
//        buttonUpdate.setOnAction(ctrl::handleUpdateMessage);
        buttonClear.setOnAction(ctrl::handleClearFields);
//        buttonDelete.setOnAction(ctrl::handleDeleteMessage);
        return hb;

    }

    private TableView<MessageTask> createMessageTaskTable() {
        TableColumn<MessageTask,String> fromColumn=new TableColumn<>("From");
        TableColumn<MessageTask,String> toColumn=new TableColumn<>("To");
        TableColumn<MessageTask,String> dateColumn=new TableColumn<>("Date");
        TableColumn<MessageTask,String> descrColumn=new TableColumn<>("Descriere");

        tableView.getColumns().addAll(fromColumn, toColumn,descrColumn,dateColumn);

        //render data
        fromColumn.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("to"));

        dateColumn.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("date"));

        descrColumn.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("description"));

        //bind data
        tableView.setItems(ctrl.getModel());

        //add listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->{
                    ctrl.showMessageTaskDetails(newValue);
                textFieldId.setEditable(false);});
        //tableView.getSelectionModel().selectFirst();
        return tableView;
    }


    public BorderPane getView(){ return borderPane;}

    private Label createLabel(String s){
        Label l=new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.RED);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

}
