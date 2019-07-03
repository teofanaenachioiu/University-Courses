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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    private static final String EXCHANGE_NAME = "logss";
    @FXML public Label traseuLabel;
    @FXML public Label numarLabel;
    @FXML public Button genereazaButton;
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
            return execute(() -> restTemplate.getForObject(urlServer + "/startJoc/"+utilizator.getUsername(), Joc.class));
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
            return execute(() -> restTemplate.getForObject(urlServer+"/currGame/"+utilizator.getUsername(), Joc.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTraseu() {
        try {
            return execute(() -> restTemplate.getForObject(urlServer+"/getTraseu/"+joc.getId(), String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void trimiteNumarGenerat(Integer nrGnereat) {
        String numareGenerateAnterior= jucatorCurent.getNumereGenerate();
        if(numareGenerateAnterior==null) numareGenerateAnterior="";
        jucatorCurent.setNumereGenerate(numareGenerateAnterior + String.valueOf(nrGnereat));
        try {
            execute(() -> restTemplate.postForObject(urlServer+"/mutare", jucatorCurent, String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }


    /******************************************************************
     *                       Implementare servicii                     *
     *******************************************************************/
    @FXML
    public void handleStart() {
        boolean haveToStartNewGame = this.isCanPlay();
        System.out.println("HAVE TO START NEW GAME " + haveToStartNewGame);
        if (haveToStartNewGame) {
            joc = incepeJoc();
            traseuLabel.setText(joc.getTraseu());
            jucatorCurent = getJucatorCurent();
        } else {
            joc = getCurrentGame();
            traseuLabel.setText(joc.getTraseu());
            jucatorCurent = getJucatorCurent();
        }
        System.out.println(joc);
        System.out.println(jucatorCurent);
        if(!jucatorCurent.isMuta()){
            genereazaButton.setDisable(true);
        }
        startButton.setDisable(true);
    }


    @FXML
    public void handleGenereaza(){
        Random rand = new Random();
        Integer nrGnereat = rand.nextInt(3);
        nrGnereat = nrGnereat +1;
        numarLabel.setText("Ati generat "+nrGnereat);
        trimiteNumarGenerat(nrGnereat);
        this.genereazaButton.setDisable(true);
        jucatorCurent.setMuta(false);
        traseuLabel.setText(getTraseu());
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
                    Platform.runLater(()->{
                        Jucator jucatorAdversar = getAdversar();
                        utilizatorLabel.setText(utilizator.getUsername()+ " vs " + jucatorAdversar.getUsername());
                    });
                }
                if(message.contains("numar") && message.contains(utilizator.getUsername())){
                    String numarulGenerat = message.substring(0,1);
                    Platform.runLater(()->{
                        numarLabel.setText("Adversarul a generat " +numarulGenerat);
                        traseuLabel.setText(getTraseu());
                        jucatorCurent.setMuta(true);
                        genereazaButton.setDisable(false);
                    });
                }
                if(message.contains("tura") && message.contains(utilizator.getUsername())){
                    Platform.runLater(()->{
                        genereazaButton.setDisable(false);

                    });
                }
                if(message.equals("done")){
                    Platform.runLater(()->{
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
