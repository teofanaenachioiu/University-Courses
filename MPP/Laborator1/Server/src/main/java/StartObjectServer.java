import repository.*;
import services.IServer;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;

    public static void main(String[] args) {
       Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/bd.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find bd.properties "+e);
            return;
        }
        IRepositoryUser userRepo=new UserRepository(serverProps);
        IRepositoryParticipant repoParticipant=new ParticipantRepository(serverProps);
        IRepositoryProba repoProba=new ProbaRepository(serverProps);
        IRepositoryInscriere repoInscriere=new InscriereRepository(serverProps);
        IServer chatServerImpl=new ServerImpl(userRepo, repoParticipant,repoProba,repoInscriere);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ObjectConcurrentServer(chatServerPort, chatServerImpl);
        try {
                server.start();
        } catch (ServerException e) {
                System.err.println("Error starting the server" + e.getMessage());
        }

    }
}
