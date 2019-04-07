package utils;

import services.IChatServer;

import java.net.Socket;


public class ChatObjectConcurrentServer extends AbsConcurrentServer {
    private IChatServer chatServer;
    public ChatObjectConcurrentServer(int port, IChatServer chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker=new ClientObjectWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
