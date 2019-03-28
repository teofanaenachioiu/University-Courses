package chat.network.utils;


import chat.network.objectprotocol.ChatClientObjectWorker;
import chat.services.IChatServer;

import java.net.Socket;


public class ChatObjectConcurrentServer extends AbsConcurrentServer {
    private IChatServer chatServer;
    public ChatObjectConcurrentServer(int port, IChatServer chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ChatClientObjectWorker worker=new ChatClientObjectWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
