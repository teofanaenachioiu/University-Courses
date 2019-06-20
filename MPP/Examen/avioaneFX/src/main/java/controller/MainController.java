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
    private static final String urlServer = "http://localhost:8080/avioane";
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    Label utilizatorLabel;
    @FXML
    TextField pozitieTextField;
    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button btn3;
    @FXML
    Button btn4;
    @FXML
    Button btn5;
    @FXML
    Button btn6;
    @FXML
    Button btn7;
    @FXML
    Button btn8;
    @FXML
    Button btn9;
    @FXML
    Button btnStart;
    @FXML
    Label labelAdversar;
    private Joc joc;
    private Jucator jucatorCurent;
    private Jucator jucatorAdversar;

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private Joc incepeJoc(Jucator jucator) {
        try {
            return execute(() -> restTemplate.postForObject(urlServer + "/startJoc", jucator, Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Jucator getJucatorCurent(Joc joc) {
        try {
            String urlJucator = urlServer + "/juc/" + joc.getId() + "/" + utilizator.getUsername();
            return execute(() -> restTemplate.getForObject(urlJucator, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Jucator getAdversar(Joc joc) {
        try {
            String urlAdversar = urlServer + "/juc/" + joc.getId() + "/" + utilizator.getUsername() + "/adversar";
            return execute(() -> restTemplate.getForObject(urlAdversar, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
        this.utilizatorLabel.setText(utilizator.getUsername() + " vs ?");
    }

    private void setCastigator() {
        try {
            String urlCastigator = urlServer + "/" + joc.getId() + "/jocuri/" + jucatorCurent.getId();
            execute(() -> restTemplate.getForObject(urlCastigator, String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mutaTura() {
        try {
            String urlCastigator = urlServer + "/" + joc.getId() + "/muta";
            execute(() -> restTemplate.getForObject(urlCastigator, Jucator.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAtac1() {
        if (jucatorAdversar.getPozitieAvion().equals(1)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac2() {
        if (jucatorAdversar.getPozitieAvion().equals(2)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac3() {
        if (jucatorAdversar.getPozitieAvion().equals(3)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac4() {
        if (jucatorAdversar.getPozitieAvion().equals(4)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac5() {
        if (jucatorAdversar.getPozitieAvion().equals(5)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac6() {
        if (jucatorAdversar.getPozitieAvion().equals(6)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac7() {
        if (jucatorAdversar.getPozitieAvion().equals(7)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac8() {
        if (jucatorAdversar.getPozitieAvion().equals(8)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleAtac9() {
        if (jucatorAdversar.getPozitieAvion().equals(9)) setCastigator();
        else mutaTura();
    }

    @FXML
    public void handleStartJoc() {
        int pozitieAvion = Integer.parseInt(pozitieTextField.getText());
        switch (pozitieAvion) {
            case 1: {
                btn1.setText("ME");
                break;
            }
            case 2: {
                btn2.setText("ME");
                break;
            }
            case 3: {
                btn3.setText("ME");
                break;
            }
            case 4: {
                btn4.setText("ME");
                break;
            }
            case 5: {
                btn5.setText("ME");
                break;
            }
            case 6: {
                btn6.setText("ME");
                break;
            }
            case 7: {
                btn7.setText("ME");
                break;
            }
            case 8: {
                btn8.setText("ME");
                break;
            }
            case 9: {
                btn9.setText("ME");
                break;
            }
        }
        pozitieTextField.setDisable(true);
        btnStart.setDisable(true);
        Jucator jucator = Jucator.builder()
                .pozitieAvion(pozitieAvion)
                .username(utilizator.getUsername())
                .build();
        this.joc = incepeJoc(jucator);

        System.out.println(joc);

        if (joc.getStareJoc().equals(StareJoc.NEW)) {
            this.jucatorCurent = this.getJucatorCurent(joc);
            labelAdversar.setVisible(true);

        }
        if (joc.getStareJoc().equals(StareJoc.IN_PROCESS)) {
            this.jucatorCurent = this.getJucatorCurent(joc);
            this.jucatorAdversar = this.getAdversar(joc);

        }
    }

    private void update() {
        jucatorCurent = getJucatorCurent(joc);
        jucatorAdversar = getAdversar(joc);

        if (!jucatorCurent.isMuta()) {
            btn1.setDisable(true);
            btn2.setDisable(true);
            btn3.setDisable(true);
            btn4.setDisable(true);
            btn5.setDisable(true);
            btn6.setDisable(true);
            btn7.setDisable(true);
            btn8.setDisable(true);
            btn9.setDisable(true);
        } else {
            btn1.setDisable(false);
            btn2.setDisable(false);
            btn3.setDisable(false);
            btn4.setDisable(false);
            btn5.setDisable(false);
            btn6.setDisable(false);
            btn7.setDisable(false);
            btn8.setDisable(false);
            btn9.setDisable(false);
        }
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
                if (message.equals("conectat")) {
                    Platform.runLater(() -> {
                        labelAdversar.setVisible(false);
                        jucatorAdversar = getAdversar(joc);
                        utilizatorLabel.setText(jucatorCurent.getUsername() + " vs " + jucatorAdversar.getUsername());
                        update();
                    });
                } else if (message.equals(jucatorCurent.getId().toString())) {
                    Platform.runLater(() -> {
                        btn1.setDisable(true);
                        btn2.setDisable(true);
                        btn3.setDisable(true);
                        btn4.setDisable(true);
                        btn5.setDisable(true);
                        btn6.setDisable(true);
                        btn7.setDisable(true);
                        btn8.setDisable(true);
                        btn9.setDisable(true);
                        showErrorMessage("Ai castigat");
                    });
                } else if (message.equals(jucatorAdversar.getId().toString())) {
                    Platform.runLater(() -> {
                        btn1.setDisable(true);
                        btn2.setDisable(true);
                        btn3.setDisable(true);
                        btn4.setDisable(true);
                        btn5.setDisable(true);
                        btn6.setDisable(true);
                        btn7.setDisable(true);
                        btn8.setDisable(true);
                        btn9.setDisable(true);
                        showErrorMessage("Ai pierdut");
                    });
                } else if (message.equals("tura")) {
                    update();
                }

            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    private static void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setContentText(text);
        message.showAndWait();
    }

}
