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
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    private static final String EXCHANGE_NAME = "logs";
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private static final String urlServer = "http://localhost:8080/anagrame";
    private RestTemplate restTemplate = new RestTemplate();
    private final ObservableList<Jucator> model = FXCollections.observableArrayList();
    @FXML
    Label utilizatorLabel;
    @FXML
    Label labelDesfasurare;
    @FXML
    TableView<Jucator> tableView;
    @FXML
    TableColumn<Jucator, String> columnNume;
    @FXML
    TableColumn<Jucator, Integer> columnPunctaj;

    @FXML
    TextField anagramaTextField;
    @FXML
    TextField corectTextField;
    @FXML
    TextField raspunsTextField;
    @FXML
    Button raspundeBtn;
    @FXML
    Button startJocBtn;
    private Joc joc;
    private Jucator jucatorCurent;
    private boolean potJuca;
    private String anagramaCurenta;
    private String anagramaAnterioara;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Jucator[] getAll() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/" + joc.getId() + "/getJucatori", Jucator[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean canPlay() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/canPlay", Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNume.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnPunctaj.setCellValueFactory(new PropertyValueFactory<>("numarPuncte"));
        tableView.setItems(model);
        try {
            listenServer();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
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

    @FXML
    void handleRaspunde() {
        String raspuns = raspunsTextField.getText();
        int maxLength = raspuns.length() < anagramaCurenta.length() ? raspuns.length() : anagramaCurenta.length();
        Integer scor = 0;
        for (int i = 0; i < maxLength; i++) {
            if (anagramaCurenta.charAt(i) == raspuns.charAt(i)) {
                scor = scor + 1;
            }
        }
        Jucator jucator = Jucator.builder()
                .username(utilizator.getUsername())
                .raspuns(raspuns)
                .numarPuncte(scor)
                .build();
        joc = sendRaspuns(jucator);
    }

    private Joc sendRaspuns(Jucator jucator) {
        try {
            return execute(() -> restTemplate.postForObject(urlServer + "/" + joc.getId() + "/raspuns", jucator, Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void handleStartJoc() {
        this.joc = this.startJoc();
        activeazaTot();
    }

    void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
        this.utilizatorLabel.setText(utilizator.getUsername());
        this.potJuca = this.canPlay();
        System.out.println(potJuca);
        this.dezactiveazaTot();
        if (!potJuca) {
            System.out.println("DEZACTIVEZ TOT");
            this.labelDesfasurare.setVisible(true);
            this.startJocBtn.setDisable(true);

        } else {
            System.out.println("START JOC");
            this.labelDesfasurare.setVisible(false);
        }
    }

    private Joc startJoc() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/startJoc", Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Joc getLastGame() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/getLastGame", Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }

    private void activeazaTot() {
        this.raspunsTextField.setDisable(false);
        this.raspundeBtn.setDisable(false);

        setPunctaje();
    }

    private void setPunctaje() {
        Jucator[] jucators = getAll();
        ArrayList<Jucator> items = new ArrayList<>(Arrays.asList(jucators));
        model.setAll(items);
    }

    private void dezactiveazaTot() {

        this.raspunsTextField.setDisable(true);
        this.raspundeBtn.setDisable(true);
    }


    /*************************************************************
     *                          Coada                            *
     *************************************************************/
    private void listenServer() throws IOException, TimeoutException {
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
                if (message.equals("startJoc") && potJuca) {
                    joc = getLastGame();
                    anagramaCurenta = joc.getCuvant1();
                    System.out.println(anagramaCurenta);
                    Platform.runLater(() -> {
                        anagramaTextField.setText(shuffleString(anagramaCurenta));
                        activeazaTot();

                    });
                }
                if (message.equals("tura2") && potJuca) {
                    joc = getLastGame();
                    anagramaAnterioara = anagramaCurenta;
                    anagramaCurenta = joc.getCuvant2();
                    System.out.println(anagramaCurenta);
                    Platform.runLater(() -> {
                        curata();
                        setPunctaje();
                    });
                }
                if (message.equals("tura3") && potJuca) {
                    joc = getLastGame();
                    anagramaAnterioara = anagramaCurenta;
                    anagramaCurenta = joc.getCuvant3();
                    System.out.println(anagramaCurenta);
                    Platform.runLater(() -> {
                        curata();
                        setPunctaje();
                    });
                }
                if (message.equals("finish") && potJuca) {
                    Platform.runLater(() ->
                    {
                        setPunctaje();
                        showMessage("Sfarsit");
                    });
                }

            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    private void curata() {
        this.anagramaTextField.setText(shuffleString(anagramaCurenta));
        this.raspunsTextField.setText("");
        this.corectTextField.setText(anagramaAnterioara);
    }

    private static void showMessage(String text) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setContentText(text);
        message.showAndWait();
    }

}
