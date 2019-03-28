package curs.sockets.client;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class StartTextClient {
    public static void main(String[] args) {
        System.out.println("Trying to connect ...");
        try(Socket client=new Socket("localhost",55555)) {
            System.out.println("Connection obtained.");
            try(PrintWriter writer=new PrintWriter(client.getOutputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                System.out.println("Sending message ...");
                String message = "Ana are mere.";

                writer.println(message);
                writer.flush();

                System.out.println("Waiting for response...");
                String response = br.readLine();
                System.out.println("Response: " + response);


            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
