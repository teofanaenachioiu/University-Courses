

import domain.User;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Server extends AbstractServer {
    public Server(int port) {
        super(port);
        System.out.println("Server");
    }

    protected void processRequest(Socket client) {
        try (BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer=new PrintWriter(client.getOutputStream())){

            String username=br.readLine();
            String parola=br.readLine();
            User user=new User(username,parola);
            User result = this.getRepo().findOne(username);
            if (result.equals(user))
                writer.println("1");
            else writer.println("0");

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}