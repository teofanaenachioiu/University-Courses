package curs.sockets.server;

import model.Person;

import java.net.Socket;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 12:17:49 PM
 */
public class SerialConcurrentServer extends ConcurrentServer {
    public SerialConcurrentServer(int port) {
        super(port);
        System.out.println("SerialConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker=new Worker(client);
        Thread tw=new Thread(worker);
        return tw;
    }

    class Worker implements Runnable{
        private Socket client;
        Worker(Socket client) {
            this.client=client;            
        }

        public void run() {
             try {
                System.out.println("Starting to process request ...  ");
                  //opening streams  - mandatory to open first the output and flush, and then the input
                ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());
                out.flush();
                ObjectInputStream in=new ObjectInputStream(client.getInputStream());
                //read message from client
                 try {
                     Object obj=in.readObject();
                     if (obj instanceof Person)    {
                         Person person=(Person)obj;
                         System.out.println("Received person "+person);
                         person.setAge(person.getAge()+1);
                         System.out.println("Sending response");
                         out.writeObject(person);
                         out.flush();
                     }

                 } catch (ClassNotFoundException e) {
                     System.out.println("Error deserializing "+e);
                 }
               
                //close the streams
                in.close();
                out.close();
                 //close connection
                client.close();
                System.out.println("Finished  processing request ...");
            } catch (IOException e) {
                System.out.println("Error in processing client request "+e);
            }
        }
    }
}
