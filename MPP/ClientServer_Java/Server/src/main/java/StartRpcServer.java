
import repository.*;
import server.ServerImpl;
import services.IServer;
import utils.AbstractServer;
import utils.RpcConcurrentServer;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties " + e);
            return;
        }
        IRepositoryUser userRepo = new UserRepository(serverProps);
        IRepositoryParticipant repoParticipant = new ParticipantRepository(serverProps);
        IRepositoryProba repoProba = new ProbaRepository(serverProps);
        IRepositoryInscriere repoInscriere = new InscriereRepository(serverProps);
        IServer chatServerImpl = new ServerImpl(userRepo, repoParticipant, repoProba, repoInscriere);

        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (utils.ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
