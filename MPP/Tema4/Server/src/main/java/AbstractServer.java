import repository.Repository;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    private Repository repo;
    public AbstractServer( int port){
              this.port=port;
    }
    public void setData(Repository repo){
        this.repo=repo;
    }
    public Repository getRepo(){
        return this.repo;
    }
    public void start() throws ServerException {
        try{
            server=new ServerSocket(port);
            while(true){
                System.out.println("Astept conexiuni ...");
                Socket client=server.accept();
                System.out.println("Client conectat ...");
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
