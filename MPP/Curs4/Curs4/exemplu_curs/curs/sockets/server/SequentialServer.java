package curs.sockets.server;

import curs.sockets.server.AbstractServer;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 11:49:21 AM
 */
public class SequentialServer extends AbstractServer {
    public SequentialServer(int port) {
        super(port);
        System.out.println("SequentialServer");
    }

    protected void processRequest(Socket client) {
        try (BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer=new PrintWriter(client.getOutputStream())){

             //read message from client
            String line=br.readLine();
            String result=line.toUpperCase()+ (new Date()).toString();

            //send result back to client
            writer.println(result);
            writer.flush();

        } catch (IOException e) {
            System.err.println("Communication error "+e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}