package com.socket.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiProblemsServer {
    public static void main(String[] args) throws IOException {

        Map<String, String> users = new ConcurrentHashMap<>();

        if (args.length != 1) {
            System.err.println("Usage: java MultiProblemsServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {

            while (listening) {
                Socket client = serverSocket.accept();

                MultiProblemsServerThread multiProblemsServerThread =
                        new MultiProblemsServerThread(client, users);
                multiProblemsServerThread.start();
            }
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}