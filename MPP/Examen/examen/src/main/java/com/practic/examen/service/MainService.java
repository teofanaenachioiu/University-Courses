package com.practic.examen.service;

import com.practic.examen.repository.UtilizatorRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MainService {
    private static final String EXCHANGE_NAME = "logs";

    private UtilizatorRepository utilizatorRepository;

    @Autowired
    MainService(UtilizatorRepository utilizatorRepository){
        this.utilizatorRepository = utilizatorRepository;
    }


    /*************************************************************
     *                          Coada                            *
     *************************************************************/
    private void initSendMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
