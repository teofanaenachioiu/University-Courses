package controller;

import com.rabbitmq.client.*;
import domain.NrJurat;
import domain.Participant;
import domain.StatusParticipant;
import domain.Utilizator;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    private static final String EXCHANGE_NAME = "logs";
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private final ObservableList<Participant> model = FXCollections.observableArrayList();
    private static final String urlServer = "http://localhost:8080/talentshow";
    RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    TableView<Participant> tableView;
    @FXML
    TableColumn<Participant, String> numeColumn;
    @FXML
    TableColumn<Participant, String> statusColumn;
    @FXML
    TextField punctajTextField;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Participant[] getAll() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer, Participant[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void adaugaPunctaj(Participant participant) {
        try {
            execute(() -> restTemplate.postForObject(urlServer, participant, Participant.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        statusColumn.setCellValueFactory(cellData -> {
                    Participant current_item = cellData.getValue();
                    String continutCamp;
                    if (current_item.getStatusParticipant().equals(StatusParticipant.COMPLETED)) {
                        Integer total = current_item.getNumarPuncteJurat1()
                                + current_item.getNumarPuncteJurat2()
                                + current_item.getNumarPuncteJurat3();
                        continutCamp = total.toString();
                    } else {
                        continutCamp = current_item.getStatusParticipant().toString();
                    }
                    return new ReadOnlyStringWrapper(continutCamp);
                }
        );
        tableView.setItems(model);
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

    void setDataInitializare(Utilizator utilizator) throws IOException, TimeoutException {
        this.utilizator = utilizator;
        String customLabel = "";
        customLabel = customLabel + utilizator.getUsername() + " - " + utilizator.getNrJurat();
        this.utilizatorLabel.setText(customLabel);
        listenServer();
        update();
    }

    @FXML
    public void handleAdaugaPunctaj() {
        if (!tableView.getSelectionModel().isEmpty()) {
            Participant participantSelected = tableView.getSelectionModel().getSelectedItem();
            Integer punctaj = Integer.parseInt(punctajTextField.getText());
            if (utilizator.getNrJurat().equals(NrJurat.JURAT1)) {
                if (participantSelected.getNumarPuncteJurat1().equals(0)) {
                    participantSelected.setNumarPuncteJurat1(punctaj);
                    this.adaugaPunctaj(participantSelected);
                } else {
                    showErrorMessage();
                }
            }
            if (utilizator.getNrJurat().equals(NrJurat.JURAT2)) {
                if (participantSelected.getNumarPuncteJurat2().equals(0)) {
                    participantSelected.setNumarPuncteJurat2(punctaj);
                    this.adaugaPunctaj(participantSelected);
                } else {
                    showErrorMessage();
                }
            }
            if (utilizator.getNrJurat().equals(NrJurat.JURAT3)) {
                if (participantSelected.getNumarPuncteJurat3().equals(0)) {
                    participantSelected.setNumarPuncteJurat3(punctaj);
                    this.adaugaPunctaj(participantSelected);
                } else {
                    showErrorMessage();
                }
            }

        }
    }

    private static void showErrorMessage() {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText("Participant deja notat!");
        message.showAndWait();
    }

    private void update() {
        Participant[] participanti = getAll();
        ArrayList<Participant> items = new ArrayList<>(Arrays.asList(participanti));
        model.setAll(items);
    }

    private void listenServer() throws IOException, TimeoutException {
        //asculta pe coada
        ConnectionFactory factoryRabbit = new ConnectionFactory();
        factoryRabbit.setHost("localhost");
        Connection connection = factoryRabbit.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                update();
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
