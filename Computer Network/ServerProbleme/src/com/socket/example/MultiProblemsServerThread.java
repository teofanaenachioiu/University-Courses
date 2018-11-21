package com.socket.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class MultiProblemsServerThread extends Thread {
    private Socket socket = null;

    private Map<String, String> users;

    public MultiProblemsServerThread(Socket socket, Map<String, String> users) {
        super("MultiProblemsServerThread");
        this.socket = socket;
        this.users = users;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ProblemsProtocol kkp = new ProblemsProtocol(users);
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye") || outputLine
                        .equals("You thought there for a second that you get another chance! Solve the assigned problem! Bye!")) {
                    break;
                }
            }
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}