package semGUI;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableViewSample extends Application {

    private TableView<Person> table = new TableView<Person>();
    private ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
                    new Person("Emma", "Jones", "emma.jones@example.com"),
                    new Person("Michael", "Brown", "michael.brown@example.com")
            );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new AnchorPane());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));



        TableColumn firstNameCol = new TableColumn("First Name");
        //firstNameCol.setMinWidth(100);
        firstNameCol.setResizable(true);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        //lastNameCol.setMinWidth(100);
        lastNameCol.setResizable(true);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

//        });

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setResizable(true);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        table.setEditable(true);


        Button b=new Button("OK");
        b.setOnAction(ev-> {
            data.get(0).setFirstName("Dana");
            data.add(new Person("sasa","sajak","jsajsk@"));
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table,b);

        ((AnchorPane) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}