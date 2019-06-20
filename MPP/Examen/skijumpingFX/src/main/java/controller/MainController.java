package controller;

import com.rabbitmq.client.*;
import domain.Participant;
import domain.StatusParticipant;
import domain.TipCategorie;
import domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    private static final String urlServer = "http://localhost:8080/concurs";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    TableView<Participant> tableView;
    @FXML
    TableColumn<Participant, String> numeColumn;
    @FXML
    TableColumn<Participant, StatusParticipant> statusColumn;
    @FXML
    TableColumn<Participant, Integer> punctajColumn;
    @FXML
    TextField punctajTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        punctajColumn.setCellValueFactory(new PropertyValueFactory<>("numarPuncte"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusParticipant"));
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

    void setUtilizator(Utilizator utilizator) throws IOException, TimeoutException {
        this.utilizator = utilizator;
        System.out.println(utilizator);
        String labelText = utilizator.getUsername() + " - " + utilizator.getTipCategorie().toString();
        this.utilizatorLabel.setText(labelText);

        Participant[] participanti = getAllParticipanti();
        System.out.println(participanti);
        ArrayList<Participant> items = new ArrayList<>(Arrays.asList(participanti));
        model.setAll(items);
        this.listenServer();
    }

    @FXML
    public void handleAcordaPunctaj(){
        Participant selectedParticipant = tableView.getSelectionModel().getSelectedItem();
        if(selectedParticipant != null){
            Integer puncte = Integer.parseInt(punctajTextField.getText());
            this.adaugaPunctaj(selectedParticipant,puncte);

        }
    }

    private void adaugaPunctaj(Participant participant, Integer puncte) {
        Integer punctePartiale = participant.getNumarPunctePartial();
        punctePartiale = punctePartiale + puncte;
        participant.setNumarPunctePartial(punctePartiale);

        if(utilizator.getTipCategorie().equals(TipCategorie.LUNGIME) && !participant.isArePunctePentruLungime()){
            participant.setArePunctePentruLungime(true);
            try {
                execute(()-> restTemplate.postForObject(urlServer, participant, Participant.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(utilizator.getTipCategorie().equals(TipCategorie.STIL) && !participant.isArePunctePentruStil()){
            participant.setArePunctePentruStil(true);
            try {
                execute(()-> restTemplate.postForObject(urlServer, participant, Participant.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(utilizator.getTipCategorie().equals(TipCategorie.ATERIZARE) && !participant.isArePunctePentruAterizare()){
            participant.setArePunctePentruAterizare(true);
            try {
                Participant participant1 = execute(()-> restTemplate.postForObject(urlServer, participant, Participant.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private void update(){
        Participant[] participanti = getAllParticipanti();
        ArrayList<Participant> items = new ArrayList<>(Arrays.asList(participanti));
        model.setAll(items);
    }

    private Participant[] getAllParticipanti(){
        try {
            return execute(()->restTemplate.getForObject(urlServer, Participant[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
