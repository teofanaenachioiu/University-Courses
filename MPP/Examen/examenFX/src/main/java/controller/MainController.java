package controller;

import com.rabbitmq.client.*;
import domain.Joc;
import domain.Jucator;
import domain.StareJoc;
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
    private static final String EXCHANGE_NAME = "logs";
    private Utilizator utilizator;
    private Stage primaryStage;
    private Scene loginScene;
    private static final String urlServer = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;

    private Joc joc;
    private Jucator jucatorCurent;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

//    private Joc incepeJoc(Jucator jucator) {
//        try {
//            return execute(() -> restTemplate.postForObject(urlServer + "/startJoc", jucator, Joc.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private Jucator getJucatorCurent(Joc joc) {
//        try {
//            String urlJucator = urlServer + "/juc/" + joc.getId() + "/" + utilizator.getUsername();
//            return execute(() -> restTemplate.getForObject(urlJucator, Jucator.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
        this.utilizatorLabel.setText(utilizator.getUsername());
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
//                    Platform.runLater(() -> {
//
//                    }
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
