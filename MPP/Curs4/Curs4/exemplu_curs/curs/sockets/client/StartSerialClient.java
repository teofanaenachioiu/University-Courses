package curs.sockets.client;

import model.Person;
import model.Student;

import java.net.Socket;
import java.io.*;


public class StartSerialClient {
    public static void main(String[] args) {
        System.out.println("Trying to connect ...");
        try(Socket client=new Socket("localhost",55555)) {

            System.out.println("Connection obtained.  ");

            //opening streams  - mandatory to open first the output and flush, and then the input
            try(ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in=new ObjectInputStream(client.getInputStream())) {
                out.flush();


                Person person = new Student("Popescu Ana", 20, 123);
                System.out.println("Sending object ..." + person);

                out.writeObject(person);
                out.flush();

                System.out.println("Waiting for response...");
                Object response = null;
                try {
                    response = in.readObject();
                } catch (ClassNotFoundException e) {
                    System.out.println("Error deserializing " + e);
                }
                System.out.println("Response: " + response);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
