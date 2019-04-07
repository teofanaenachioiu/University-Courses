package utils;

import rpcprotocol.ClientRpcReflectionWorker;
import services.IServer;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IServer chatServer;
    public RpcConcurrentServer(int port, IServer chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
