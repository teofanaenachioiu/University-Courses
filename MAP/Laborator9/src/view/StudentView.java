package view;

import domain.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;
import java.util.*;

import static javafx.scene.paint.Color.*;



public class StudentView   {

    private StudentController controller;

    private BorderPane borderPane;
    TableView<Student> tableView=new TableView<>();

    public StudentView(StudentController controller) {
        this.controller = controller;
        initView();
    }

    TextField textFieldId=new TextField();
    TextField textFieldNume=new TextField();
    TextField textFieldGrupa=new TextField();
    TextField textFieldEmail=new TextField();
    TextField textFieldProf=new TextField();

    Button buttonAdd=new Button("Add");
    Button buttonUpdate=new Button("Update");
    Button buttonDelete=new Button("Delete");
    Button buttonClear= new Button("Clear All");


    TextField textInput=new TextField();
    Label filterLabel=new Label("Give input");
    Button buttonApply= new Button("Apply");

    AutoCompleteTextField autoCompleteTextField;

    private void initView() {
        borderPane=new BorderPane();
        //top AnchorPane
        borderPane.setTop(initTop());
        //left
        borderPane.setLeft(initLeft());
        //center
        borderPane.setCenter(initCenter());

        Label copyright=new Label("Copyright © Teofana Enachioiu");
        copyright.setTextFill(WHITE);

        AnchorPane a=new AnchorPane(copyright);
        AnchorPane.setBottomAnchor(copyright,10d);
        AnchorPane.setLeftAnchor(copyright,10d);
        borderPane.setBottom(a);

        Image img = new Image(new File("./src/resources/image1.jpg").toURI().toString());


        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        borderPane.setBackground(new Background(new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }

    private AnchorPane initTop()  {
        AnchorPane topAnchorPane=new AnchorPane();

        Label titleLabel=new Label("Student Management System");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,40d);
        AnchorPane.setRightAnchor(titleLabel,50d);
        AnchorPane.setBottomAnchor(titleLabel,30d);

        Font f = new Font("Book Antiqua", 40);
        titleLabel.setFont(f);
        titleLabel.setTextFill(WHITE);
        titleLabel.setStyle("-fx-font-weight: bold");

        return topAnchorPane;
    }

    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane=createGridPane();
        //anchor the gridpane
        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,20d);

        HBox buttonsGroups=createButtons();
        HBox filterGroups=createFilterArea();
        HBox filterDetailsGroups=createFilterDetails();

        //anchor the buttons
        AnchorPane.setTopAnchor(buttonsGroups,170d);
        AnchorPane.setLeftAnchor(buttonsGroups,20d);
        // buttonsGroups.setPadding(new Insets(30));


        AnchorPane.setTopAnchor(filterGroups,210d);
        AnchorPane.setLeftAnchor(filterGroups,20d);

        AnchorPane.setTopAnchor(filterDetailsGroups,250d);
        AnchorPane.setLeftAnchor(filterDetailsGroups,20d);

        centerAnchorPane.getChildren().add(buttonsGroups);
        centerAnchorPane.getChildren().add(filterGroups);
        centerAnchorPane.getChildren().add(filterDetailsGroups);

        return centerAnchorPane;
    }


    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        //Search area


        Image img = new Image(new File("./src/resources/search1.png").toURI().toString());
        Button buttonImage=new Button();
        ImageView imageView=new ImageView(img);

        imageView.setFitWidth(10);
        imageView.setFitHeight(10);

        buttonImage.setGraphic(imageView);



        SortedSet<String> studentList=new TreeSet<String>(controller.listaNumeStudenti());
        autoCompleteTextField=new AutoCompleteTextField(studentList);

        HBox hb=new HBox(3,buttonImage,autoCompleteTextField);

        buttonImage.setOnAction(controller::handleSearchButton);
        autoCompleteTextField.setOnKeyPressed(controller::handleSearchField);

        tableView=createStudentTable();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);
       // AnchorPane.setTopAnchor(tableView,30d);


        leftAnchorPane.getChildren().add(hb);
        AnchorPane.setLeftAnchor(hb,20d);
        AnchorPane.setBottomAnchor(hb,20d);
        return  leftAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();

        gridPaneMessageDetails.setHgap(5);
        gridPaneMessageDetails.setVgap(5);

        Label labelId=createLabel("Id:");
        Label labelNume=createLabel("Name:");
        Label labelGrupa=createLabel("Group:");
        Label labelEmail=createLabel("Email:");
        Label labelProf=createLabel("Teacher:");

        gridPaneMessageDetails.add(labelId,0,0);
        gridPaneMessageDetails.add(labelNume,0,1);
        gridPaneMessageDetails.add(labelGrupa,0,2);
        gridPaneMessageDetails.add(labelEmail,0,3);
        gridPaneMessageDetails.add(labelProf,0,4);

        gridPaneMessageDetails.add(textFieldId,1,0);
        gridPaneMessageDetails.add(textFieldNume,1,1);
        gridPaneMessageDetails.add(textFieldGrupa,1,2);
        gridPaneMessageDetails.add(textFieldEmail, 1,3);
        gridPaneMessageDetails.add(textFieldProf,1,4);


        textFieldId.setStyle("-fx-opacity: 0.7");
        textFieldNume.setStyle("-fx-opacity: 0.7");
        textFieldGrupa.setStyle("-fx-opacity: 0.7");
        textFieldEmail.setStyle("-fx-opacity: 0.7");
        textFieldProf.setStyle("-fx-opacity: 0.7");

        textFieldId.setPromptText("Set ID...");
        textFieldNume.setPromptText("Set name...");
        textFieldGrupa.setPromptText("Set group...");
        textFieldEmail.setPromptText("Set email...");
        textFieldProf.setPromptText("Set teacher...");

        ColumnConstraints c1=new ColumnConstraints();
        c1.setPrefWidth(60d);

        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(250d);

        gridPaneMessageDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneMessageDetails;
    }

    private HBox createFilterDetails() {
        filterLabel.setFont(new Font(12));
        filterLabel.setTextFill(WHITE);
        filterLabel.setStyle("-fx-font-weight: bold");
        textInput.setPromptText("Type here");
        HBox hb=new HBox(5, filterLabel,textInput,buttonApply);
        filterLabel.setVisible(false);
        textInput.setVisible(false);
        buttonApply.setVisible(false);
        return hb;
    }

    public HBox createFilterArea(){
        // create a combo box

        Label filter=new Label("Filter by");
        filter.setFont(new Font(12));
        filter.setTextFill(WHITE);
        filter.setStyle("-fx-font-weight: bold");

        //Button buttonApply= new Button("Apply");


        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "(no filter)",
                "by teacher",
                "by group"
        );

        comboBox.setPrefWidth(165d);
        comboBox.setValue("(no filter)");

        textInput.setOpacity(0.7d);

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboBox.getValue().toString().equals("(no filter)")) {
                    tableView.setItems(controller.getModel());
                    filterLabel.setVisible(false);
                    textInput.setVisible(false);
                    buttonApply.setVisible(false);
                    textInput.setText("");
                }
                if(comboBox.getValue().toString().equals("by teacher")|| comboBox.getValue().toString().equals("by group")) {
                    filterLabel.setVisible(true);
                    textInput.setVisible(true);
                    buttonApply.setVisible(true);
                    textInput.setText("");
                }

            }
        });

        textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    if(comboBox.getValue().toString().equals("by teacher")) {
                        tableView.setItems(controller.getFilterTeacher(textInput.getText()));
                        textInput.setText("");
                    }
                    if(comboBox.getValue().toString().equals("by group")) {
                        tableView.setItems(controller.getFilterGroup(textInput.getText()));
                        textInput.setText("");
                    }
                }
            }
        });

        buttonApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboBox.getValue().toString().equals("by teacher")) {
                    tableView.setItems(controller.getFilterTeacher(textInput.getText()));
                    textInput.setText("");
                }
                if(comboBox.getValue().toString().equals("by group")) {
                    tableView.setItems(controller.getFilterGroup(textInput.getText()));
                    textInput.setText("");
                }
            }
        });

        HBox hb=new HBox(3, filter,comboBox);
        return hb;
    }

    public HBox createButtons(){

        HBox hb=new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);

      //  HBox hbb=new HBox(10, button);
        buttonAdd.setOnAction(controller::handleAddStudent);
        buttonUpdate.setOnAction(controller::handleUpdateStudentButton);
        buttonDelete.setOnAction(controller::handleDeleteStudentButton);
        buttonClear.setOnAction(controller::handleClearStudentButton);

        return hb;

    }

    private TableView<Student> createStudentTable() {
        TableColumn<Student,String> numeColumn=new TableColumn<>("Name");
        TableColumn<Student,String> grupaColumn=new TableColumn<>("Group");
        TableColumn<Student,String> emailColumn=new TableColumn<>("Email");
        TableColumn<Student,String> profColumn=new TableColumn<>("Teacher");

        tableView.getColumns().addAll(numeColumn, grupaColumn,emailColumn,profColumn);

        //render data
        numeColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        grupaColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        profColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("indrumatorLab"));

        //editare celule
        tableView.setEditable(true);

        numeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student student=tableView.getSelectionModel().getSelectedItem();
                student.setNume(event.getNewValue());
                textFieldNume.setText(student.getNume());
                controller.handleUpdateStudent(student);
            }
        });

        grupaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student student=tableView.getSelectionModel().getSelectedItem();
                student.setGrupa(event.getNewValue());
                textFieldGrupa.setText(student.getGrupa());
                controller.handleUpdateStudent(student);
            }
        });

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student student=tableView.getSelectionModel().getSelectedItem();
                student.setEmail(event.getNewValue());
                textFieldEmail.setText(student.getEmail());
                controller.handleUpdateStudent(student);
            }
        });

        profColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        profColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                Student student=tableView.getSelectionModel().getSelectedItem();
                student.setIndrumatorLab(event.getNewValue());
                textFieldProf.setText(student.getIndrumatorLab());
                controller.handleUpdateStudent(student);
            }
        });


        //bind data
        tableView.setItems(controller.getModel());

        //add listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->{
                    controller.showStudentDetails(newValue);
                    });
        //tableView.getSelectionModel().selectFirst();

        return tableView;
    }


    public BorderPane getView(){ return borderPane;}

    private Label createLabel(String s){
        Label l=new Label(s);
        l.setFont(new Font(12));
        l.setTextFill(WHITE);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }



}
