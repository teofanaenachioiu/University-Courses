package controller;

import com.rabbitmq.client.*;
import domain.Joc;
import domain.Jucator;
import domain.Utilizator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    private static final String EXCHANGE_NAME = "logss";
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public Label label4;
    @FXML
    public Label label5;
    @FXML
    public Label label6;
    @FXML
    public Label label7;
    @FXML
    public Label label8;
    @FXML
    public Label label9;
    @FXML
    public Label label10;
    @FXML
    public Label label11;
    @FXML
    public Label label12;
    @FXML
    public Label label13;
    @FXML
    public Label label14;
    @FXML
    public Label label15;
    @FXML
    public Label label16;
    @FXML
    public Label invalidLabel;
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private static final String urlServer = "http://localhost:8080/joc";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    Label asteaptaLabel;

    @FXML
    Label pozitieAdversarLabel;
    private Joc joc;
    private Jucator jucatorCurent;
    @FXML
    TextField pozitie1TextField;
    @FXML
    TextField pozitie2TextField;
    @FXML
    TextField atacaTextField;
    @FXML
    Button atacaButton;
    @FXML
    Button startButton;


    /********************************************************************
     *                          Client REST                             *
     ********************************************************************/
    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Joc incepeJoc() {
        try {
            Jucator jucator = Jucator.builder()
                    .username(utilizator.getUsername())
                    .pozitie1(Integer.parseInt(pozitie1TextField.getText()))
                    .pozitie2(Integer.parseInt(pozitie2TextField.getText()))
                    .build();
            return execute(() -> restTemplate.postForObject(urlServer + "/startJoc", jucator, Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Jucator getJucatorCurent() {
        try {
            String urlJucator = urlServer + "/" + joc.getId() + "/getJucator/" + utilizator.getUsername();
            return execute(() -> restTemplate.getForObject(urlJucator, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Jucator getAdversar() {
        try {
            String urlJucator = urlServer + "/" + joc.getId() + "/getAdversar/" + utilizator.getUsername();
            return execute(() -> restTemplate.getForObject(urlJucator, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean trimitePozitie() {
        try {
            String pozitieAtac = atacaTextField.getText();
            String urlJucator = urlServer + "/" + joc.getId() + "/ghiceste/" + jucatorCurent.getId() + "/" + pozitieAtac;
            return execute(() -> restTemplate.getForObject(urlJucator, Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isCanPlay() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/haveToStartNewGame", Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Joc getCurrentGame() {
        try {
            Jucator jucator = Jucator.builder()
                    .username(utilizator.getUsername())
                    .pozitie1(Integer.parseInt(pozitie1TextField.getText()))
                    .pozitie2(Integer.parseInt(pozitie2TextField.getText()))
                    .build();
            return execute(() -> restTemplate.postForObject(urlServer + "/currGame", jucator, Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    void setDateInitializare(Utilizator utilizator) throws Exception {
        this.utilizator = utilizator;
        this.utilizatorLabel.setText(utilizator.getUsername());
        listenServer();
        atacaTextField.setDisable(true);
        atacaButton.setDisable(true);
    }

    private boolean valideazaPozitii() {
        int pozitia1 = Integer.parseInt(pozitie1TextField.getText()) -1;
        int pozitia2 = Integer.parseInt(pozitie2TextField.getText()) -1;
        if (pozitia1 / 4 == pozitia2 / 4) return true;
        return pozitia1 % 4 == pozitia2 % 4;
    }


    /******************************************************************
     *                       Implementare servicii                     *
     *******************************************************************/
    @FXML
    public void handleStart() {
        if (!valideazaPozitii()) {
            invalidLabel.setVisible(true);
            return;
        }
        boolean haveToStartNewGame = this.isCanPlay();
        System.out.println("HAVE TO START NEW GAME " + haveToStartNewGame);
        invalidLabel.setVisible(false);
        if (haveToStartNewGame) {
            joc = incepeJoc();
            jucatorCurent = getJucatorCurent();
            this.asteaptaLabel.setVisible(true);
        } else {
            joc = getCurrentGame();
            jucatorCurent = getJucatorCurent();
        }
        System.out.println(joc);
        System.out.println(jucatorCurent);
        if (!jucatorCurent.isMuta()) {
            atacaButton.setDisable(true);
            atacaTextField.setDisable(true);
        } else {
            atacaTextField.setDisable(false);
            atacaButton.setDisable(false);
        }
        pozitie1TextField.setDisable(true);
        pozitie2TextField.setDisable(true);
        atacaTextField.setDisable(false);
        startButton.setDisable(true);

        Integer pozitie1 = Integer.parseInt(pozitie1TextField.getText());
        Integer pozitie2 = Integer.parseInt(pozitie2TextField.getText());
        setPozitiePeTabela(pozitie1, "@");
        setPozitiePeTabela(pozitie2, "@");
    }

    private void setPozitiePeTabela(Integer pozitie, String elem) {
        switch (pozitie) {
            case 1: {
                label1.setText(elem);
                break;
            }
            case 2: {
                label2.setText(elem);
                break;
            }
            case 3: {
                label3.setText(elem);
                break;
            }
            case 4: {
                label4.setText(elem);
                break;
            }
            case 5: {
                label5.setText(elem);
                break;
            }
            case 6: {
                label6.setText(elem);
                break;
            }
            case 7: {
                label7.setText(elem);
                break;
            }
            case 8: {
                label8.setText(elem);
                break;
            }
            case 9: {
                label9.setText(elem);
                break;
            }
            case 10: {
                label10.setText(elem);
                break;
            }
            case 11: {
                label11.setText(elem);
                break;
            }
            case 12: {
                label12.setText(elem);
                break;
            }
            case 13: {
                label13.setText(elem);
                break;
            }
            case 14: {
                label14.setText(elem);
                break;
            }
            case 15: {
                label15.setText(elem);
                break;
            }
            case 16: {
                label16.setText(elem);
                break;
            }
        }
    }

    @FXML
    public void handleAtaca() {
        Boolean amGhicit = trimitePozitie();
        if (amGhicit){
            showMessage("Ati ghicit o pozitie a adversarului!");
        }
        pozitieAdversarLabel.setText("Ati atacat pozitia " + atacaTextField.getText());
        jucatorCurent.setMuta(false);
        atacaButton.setDisable(true);
        atacaTextField.setDisable(true);
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

                if (message.contains("start")) {
                    Platform.runLater(() -> {
                        Jucator jucatorAdversar = getAdversar();
                        utilizatorLabel.setText(utilizator.getUsername() + " vs " + jucatorAdversar.getUsername());
                        asteaptaLabel.setVisible(false);
                    });
                }

                if (message.contains("tura") && message.contains(utilizator.getUsername())) {
                    Platform.runLater(() -> {
                        String pozitie = message.split("/")[1];
                        atacaButton.setDisable(false);
                        atacaTextField.setDisable(false);
                        pozitieAdversarLabel.setText("Adversarul a atracat pozitia " + pozitie);
                        setPozitiePeTabela(Integer.parseInt(pozitie), "X");
                    });
                }

                if (message.contains("done")) {
                    Platform.runLater(() -> {
                        String castigator = message.split("/")[1];
                        showMessage("Game over! " + castigator + " a castigat!");
                        atacaTextField.setDisable(true);
                        atacaButton.setDisable(true);
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
