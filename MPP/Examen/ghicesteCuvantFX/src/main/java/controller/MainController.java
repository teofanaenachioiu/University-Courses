package controller;

import com.rabbitmq.client.*;
import domain.Joc;
import domain.Jucator;
import domain.Utilizator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private static final String QUEUE_NAME = "logs";
    private final ObservableList<Jucator> model = FXCollections.observableArrayList();
    private static final String EXCHANGE_NAME = "logs";
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private static final String urlServer = "http://localhost:8080/joc";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    TableView<Jucator> tableView;
    @FXML
    TableColumn<Jucator, String> columnNume;
    @FXML
    TableColumn<Jucator, String> columnCuvant;
    @FXML
    Button startButton;
    @FXML
    Button raspundeButton;
    @FXML
    Button salveazaButton;
    @FXML
    Label asteaptaLabel;
    @FXML
    TextField cuvantTextField;
    @FXML
    TextField literaTextField;

    private Joc joc;
    private Jucator jucatorCurent;

    /*****************
     *  client rest  *
     *****************/
    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private void incepeJoc() {
        try {
            execute(() -> restTemplate.getForObject(urlServer + "/startJoc", Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Jucator getJucator(Integer idJoc, String username) {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/" + idJoc + "/getJucator/" + username, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Jucator[] getAdversari(Integer idJoc) {
        try {
            return execute(() -> restTemplate.getForObject
                    (urlServer + "/" + idJoc + "/getAdversari/" + utilizator.getUsername(), Jucator[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void adaugaCuvant(String cuvant) {
        Jucator jucator = Jucator.builder()
                .id(jucatorCurent.getId())
                .cuvantPropus(cuvant)
                .build();
        try {
            execute(() -> restTemplate.postForObject(urlServer + "/adaugaCuvant", jucator, String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCanPlay() throws Exception {
        return execute(() -> restTemplate.getForObject(urlServer + "/canPlay", Boolean.class));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    void setUtilizator(Utilizator utilizator) throws Exception {
        this.utilizator = utilizator;
        this.utilizatorLabel.setText(utilizator.getUsername());
        listenServer();
        boolean canPlay = this.isCanPlay();
        System.out.println("CAN PLAY " + canPlay);
        if (!canPlay) {
            this.asteaptaLabel.setVisible(true);
            this.startButton.setDisable(true);
        } else {
            this.asteaptaLabel.setVisible(false);
        }
        blocheazaCuvant(true);
        blocheazaRaspunde(true);
        columnNume.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnCuvant.setCellValueFactory(new PropertyValueFactory<>("cuvantPropus"));
    }

    @FXML
    void handleStartJoc() {
        this.incepeJoc();
//        jucatorCurent = this.getJucator(joc.getId(), utilizator.getUsername());
    }

    @FXML
    void handleRaspunde() throws Exception {
        Integer idAdversar = tableView.getSelectionModel().getSelectedItem().getId();
        verificaRespuns(idAdversar);
        nuETuraMea();
    }

    @FXML
    void handleSalveaza() {
        String cuvant = cuvantTextField.getText();
        adaugaCuvant(cuvant);
        blocheazaCuvant(true);
        if(jucatorCurent.isMuta()){
            blocheazaRaspunde(false);
        }
    }

    private void blocheazaCuvant(boolean cum) {
        this.salveazaButton.setDisable(cum);
        this.cuvantTextField.setDisable(cum);
    }

    private void blocheazaRaspunde(boolean cum) {
        this.raspundeButton.setDisable(cum);
        this.literaTextField.setDisable(cum);
    }

    private void verificaRespuns(Integer idAdversar) throws Exception {
        Jucator jucator = Jucator.builder()
                .id(jucatorCurent.getId())
                .cuvantPropus(literaTextField.getText())
                .build();
        execute(() -> restTemplate.postForObject(urlServer + "/raspunde/" + idAdversar, jucator, String.class));
    }


    private void nuETuraMea() {
        this.raspundeButton.setDisable(true);
        this.literaTextField.setDisable(true);
        jucatorCurent.setMuta(false);
    }

    private void setGame() throws Exception {
        joc = execute(() -> restTemplate.getForObject(urlServer + "/currGame", Joc.class));
        jucatorCurent = getJucator(joc.getId(), utilizator.getUsername());
        System.out.println("SET GAME");
        System.out.println(joc);
        System.out.println(jucatorCurent);
        assert jucatorCurent != null;
        if (!jucatorCurent.isMuta()) {
            blocheazaRaspunde(true);
        }
        setModel();
    }

    private void setModel() {
        System.out.println("SET MODEL");
        System.out.println(joc);
        Jucator[] participanti = getAdversari(joc.getId());
        ArrayList<Jucator> items = new ArrayList<>(Arrays.asList(participanti));
        model.setAll(items);
    }

    /*************************************************************
     *                          Coada                            *
     *************************************************************/
    private void listenServer() throws IOException, TimeoutException {
        ConnectionFactory factoryRabbit = new ConnectionFactory();
        factoryRabbit.setHost("localhost");
        Connection connection = factoryRabbit.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(QUEUE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                if (message.equals("start")) {
                    try {
                        setGame();
                        blocheazaCuvant(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (message.equals("cuvantNou") || message.contains("corect")) {
                    Platform.runLater(() -> {
                        setModel();
                    });
                }
                if (message.contains("tura") && message.contains(utilizator.getUsername())) {
                    Platform.runLater(() -> {
                        jucatorCurent.setMuta(true);
                        blocheazaRaspunde(false);
                    });
                }
                if (message.contains("done")) {
                    Platform.runLater(() -> {
                        showMessage("Game over");
                    });
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    private static void showMessage(String text) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setContentText(text);
        message.showAndWait();
    }

}
