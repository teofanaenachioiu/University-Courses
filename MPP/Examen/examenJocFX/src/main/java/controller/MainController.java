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
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    private static final String EXCHANGE_NAME = "logss";
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private static final String urlServer = "http://localhost:8080/joc";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    Label asteaptaLabel;
    private Joc joc;
    private Jucator jucatorCurent;
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
            return execute(() -> restTemplate.getForObject(urlServer + "/startJoc", Joc.class));
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

    private boolean isCanPlay() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer + "/canPlay", Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Joc getCurrentGame() {
        try {
            return execute(() -> restTemplate.getForObject("http://localhost:8080/joc/currGame", Joc.class));
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

        boolean canPlay = this.isCanPlay();
        System.out.println("CAN PLAY " + canPlay);
//        if (!canPlay) {
//            this.asteaptaLabel.setVisible(true);
//            this.startButton.setDisable(true);
//        } else {
//            this.asteaptaLabel.setVisible(false);
//            this.startButton.setDisable(false);
//        }
    }


    /******************************************************************
     *                       Implementare servicii                     *
     *******************************************************************/
    @FXML
    public void handleStart() {
        incepeJoc();
    }

    private void setGame() {
        joc = getCurrentGame();
        jucatorCurent = getJucatorCurent();
        System.out.println("SET GAME");
        System.out.println(joc);
        System.out.println(jucatorCurent);
        startButton.setDisable(true);
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
                // mesajele

                if (message.contains("start")) {
                    setGame();
                }

                if(message.equals("done")){
                    showMessage("Game over");
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
