package curs.sockets.server;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 14, 2011
 * Time: 12:24:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyConcurrentServer extends ConcurrentServer{
    public MyConcurrentServer(int port) {
        super(port);
    }

    protected Thread createWorker(Socket client){
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
            System.out.println("Starting to process request ...");
            try( BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter writer=new PrintWriter(client.getOutputStream())) {

                //read message from client
                String line=br.readLine();
                String result=line.toUpperCase()+' ' +(new Date()).toString();
                //send result back to client

                writer.println(result);
                writer.flush();

                System.out.println("Finished  processing request ...");
            } catch (IOException e) {
                System.out.println("Error in processing client request "+e);
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
