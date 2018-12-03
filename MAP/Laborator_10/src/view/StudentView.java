package view;

import domain.Student;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;
import java.util.*;

import static javafx.scene.paint.Color.*;

public class StudentView   {

    private StudentController controller;

    private BorderPane borderPane;
    TableView<Student> tableView=new TableView<>();

    TextField textFieldId=new TextField();
    TextField textFieldNume=new TextField();
    TextField textFieldGrupa=new TextField();
    TextField textFieldEmail=new TextField();
    TextField textFieldProf=new TextField();

    private Button buttonAdd=new Button("Add");
    private Button buttonUpdate=new Button("Update");
    private Button buttonDelete=new Button("Delete");
    private Button buttonClear= new Button("Clear All");

    TextField textInput=new TextField();
    Label filterLabel=new Label("Give input");
    Button buttonApply= new Button("Apply");

    AutoCompleteTextField autoCompleteTextField;

    ComboBox comboBox = new ComboBox();

    public StudentView(StudentController controller) {
        this.controller = controller;
        initView();
    }

    public BorderPane getBorderPaneStudent() {
        return borderPane;
    }

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

        Button buttonImage=new Button();

        Image img = new Image(new File("./src/resources/exit.png").toURI().toString());
        ImageView imageView=new ImageView(img);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        buttonImage.setGraphic(imageView);
        buttonImage.setOnAction(controller::handleExit);

        topAnchorPane.getChildren().add(buttonImage);
        AnchorPane.setTopAnchor(buttonImage,40d);
        AnchorPane.setLeftAnchor(buttonImage,50d);
        AnchorPane.setBottomAnchor(buttonImage,30d);

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

    private AnchorPane initCenter(){
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

    private AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();

        tableView=createStudentTable();
        HBox hb=createSearchArea();

        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);

        leftAnchorPane.getChildren().add(hb);
        AnchorPane.setLeftAnchor(hb,20d);
        AnchorPane.setBottomAnchor(hb,20d);

        return  leftAnchorPane;
    }

    private HBox createSearchArea(){
        Button buttonImage=new Button();

        Image img = new Image(new File("./src/resources/search1.png").toURI().toString());
        ImageView imageView=new ImageView(img);
        imageView.setFitWidth(10);
        imageView.setFitHeight(10);

        buttonImage.setGraphic(imageView);
        buttonImage.setOnAction(controller::handleSearchButton);

        SortedSet<String> studentList=new TreeSet<>(controller.listaNumeStudenti());
        autoCompleteTextField=new AutoCompleteTextField(studentList);
        autoCompleteTextField.setOnKeyPressed(controller::handleSearchField);

        return new HBox(3,buttonImage,autoCompleteTextField);
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
        filterLabel.setVisible(false);

        textInput.setPromptText("Type here");
        textInput.setVisible(false);
        textInput.setOpacity(0.7d);
        textInput.setOnKeyPressed(controller::handleComboBoxKey);

        buttonApply.setVisible(false);
        buttonApply.setOnAction(controller::handleApplyButton);

        return new HBox(5, filterLabel,textInput,buttonApply);
    }

    private HBox createFilterArea(){
        Label filter=new Label("Filter by");
        filter.setFont(new Font(12));
        filter.setTextFill(WHITE);
        filter.setStyle("-fx-font-weight: bold");

        comboBox.getItems().addAll(
                "(no filter)",
                "by teacher",
                "by group"
        );
        comboBox.setPrefWidth(165d);
        comboBox.setValue("(no filter)");

        comboBox.setOnAction(controller::handleComboBox);

        return new HBox(3, filter,comboBox);
    }

    private HBox createButtons(){
        buttonAdd.setOnAction(controller::handleAddStudent);
        buttonUpdate.setOnAction(controller::handleUpdateStudentButton);
        buttonDelete.setOnAction(controller::handleDeleteStudentButton);
        buttonClear.setOnAction(controller::handleClearStudentButton);

        return new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);
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
        numeColumn.setOnEditCommit(controller::handleEditColumnNume);

        grupaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaColumn.setOnEditCommit(controller::handleEditColumnGrupa);

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(controller::handleEditColumnEmail);

        profColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        profColumn.setOnEditCommit(controller::handleEditColumnProf);

        //bind data
        tableView.setItems(controller.getModel());

        //add listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->{
                    controller.showStudentDetails(newValue);
                    });
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
