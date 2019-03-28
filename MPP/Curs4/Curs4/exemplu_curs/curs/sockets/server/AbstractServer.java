package curs.sockets.server;

import curs.sockets.server.ServerException;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 11:41:16 AM
 */
public abstract class AbstractServer {
      private int port;
    private ServerSocket server=null;
    public AbstractServer( int port){
              this.port=port;
    }

    public void start() throws ServerException {
        try{
            server=new ServerSocket(port);
            while(true){
                System.out.println("Waiting for clients ...");
                Socket client=server.accept();
                System.out.println("Client connected ...");
                processRequest(client);
            }
        } catch (IOException e) {
            throw new ServerException("Starting server errror ",e);
        }finally {
            try{
                server.close();
            } catch (IOException e) {
                throw new ServerException("Closing server error ", e);
            }
        }
    }

    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException{
        try{
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
