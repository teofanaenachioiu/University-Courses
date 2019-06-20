package controller;

import domain.Coordonator;
import domain.Participant;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class ControllerCoordonator implements Initializable {
    private Coordonator coordonator;
    private Stage primaryStage;
    private Scene loginScene;
    private final ObservableList<Participant> model = FXCollections.observableArrayList();
    private static final String urlCercetasi = "http://localhost:8080/cercetasi";
    RestTemplate restTemplate = new RestTemplate();

    @FXML
    TableView<Participant> tableView;
    @FXML
    TableColumn<Participant, String> numeColumn;
    @FXML
    TableColumn<Participant, Integer> punctColumn;
    @FXML
    TableColumn<Participant, String> oraColumn;
    @FXML
    Label coordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        punctColumn.setCellValueFactory(new PropertyValueFactory<>("ultimulPunctDeControl"));
        oraColumn.setCellValueFactory(cellData -> {
            Participant current_item = cellData.getValue();
            Date dataSosirii = new Date(current_item.getOraSosirii());
            String ora = "";
            ora = ora + dataSosirii.getHours() + ":" + dataSosirii.getMinutes();
            return new ReadOnlyStringWrapper(ora);
        });
        tableView.setItems(model);
    }

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Participant[] getAll() {
        try {
            return execute(() -> restTemplate.getForObject(urlCercetasi + "/"+coordonator.getUsername(), Participant[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void passParticipant(Participant participant) throws Exception {
        execute(()->restTemplate.postForObject(urlCercetasi + "/passed", participant, String.class));
    }

    void setData() {
        Participant[] participanti = getAll();
        ArrayList<Participant> items = new ArrayList<>(Arrays.asList(participanti));
        model.setAll(items);
    }

    @FXML
    void handlePassed()  {
        Participant participant = tableView.getSelectionModel().getSelectedItem();
        System.out.println(participant);
        if(participant!=null){
            try {
                this.passParticipant(participant);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleLogout() {
        this.primaryStage.setScene(loginScene);
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void setLoginScene(Scene mainScene) {
        this.loginScene = mainScene;
    }

    void setCoordonator(Coordonator coordonator) {
        this.coordonator = coordonator;
        System.out.println(coordonator);
        this.coordLabel.setText("Punct control: " + this.coordonator.getPunctControl());
    }
}
