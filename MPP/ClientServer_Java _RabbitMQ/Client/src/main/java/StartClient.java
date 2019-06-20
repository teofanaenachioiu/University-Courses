import GUI.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServer;

import com.rabbitmq.client.*;
import services.MyAppException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StartClient extends Application {
    private static final String EXCHANGE_NAME = "logs";

    public void start(Stage primaryStage){
        System.out.println("In start");
        try {

            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IServer server=(IServer) factory.getBean("concursService");
            System.out.println("Obtained a reference to remote chat server");
            System.out.println("Hello!");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewLogin.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene mainMenuScene = new Scene(rootLayout, 600, 400);

            ControllerLogin controllerLogin = loader.getController();
            controllerLogin.init(server, mainMenuScene);

            controllerLogin.setPrimaryStage(primaryStage);
            listenServer(controllerLogin);

            primaryStage.setScene(mainMenuScene);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Concurs");

            primaryStage.show();



        }catch (Exception e) {
            System.err.println("Application Initialization  exception:"+e);
            e.printStackTrace();
        }
    }

    private void listenServer(ControllerLogin controllerLogin) throws IOException, TimeoutException {
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
                try {
                    controllerLogin.update();
                } catch (MyAppException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
