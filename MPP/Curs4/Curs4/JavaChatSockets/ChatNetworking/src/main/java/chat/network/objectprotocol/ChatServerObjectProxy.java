package chat.network.objectprotocol;

import chat.model.Message;
import chat.model.User;
import chat.network.dto.DTOUtils;
import chat.network.dto.MessageDTO;
import chat.network.dto.UserDTO;
import chat.services.ChatException;
import chat.services.IChatObserver;
import chat.services.IChatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ChatServerObjectProxy implements IChatServer {
    private String host;
    private int port;

    private IChatObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ChatServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(User user, IChatObserver client) throws ChatException {
        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LoginRequest(udto));
        Response response=readResponse();
        if (response instanceof OkResponse){
            this.client=client;
            return;
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ChatException(err.getMessage());
        }
    }

    public void sendMessage(Message message) throws ChatException {
        MessageDTO mdto=DTOUtils.getDTO(message);
        sendRequest(new SendMessageRequest(mdto));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ChatException(err.getMessage());
        }
    }

    public void logout(User user, IChatObserver client) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(user);
        sendRequest(new LogoutRequest(udto));
        Response response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ChatException(err.getMessage());
        }
    }



    public User[] getLoggedFriends(User user) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(user);
        sendRequest(new GetLoggedFriendsRequest(udto));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ChatException(err.getMessage());
        }
        GetLoggedFriendsResponse resp=(GetLoggedFriendsResponse)response;
        UserDTO[] frDTO=resp.getFriends();
        User[] friends=DTOUtils.getFromDTO(frDTO);
        return friends;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws ChatException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ChatException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ChatException {
        Response response=null;
        try{
            /*synchronized (responses){
                responses.wait();
            }
            response = responses.remove(0);    */
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ChatException {
         try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update){
        if (update instanceof FriendLoggedInResponse){

            FriendLoggedInResponse frUpd=(FriendLoggedInResponse)update;
            User friend=DTOUtils.getFromDTO(frUpd.getFriend());
            System.out.println("Friend logged in "+friend);
            try {
                client.friendLoggedIn(friend);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }
        if (update instanceof FriendLoggedOutResponse){
            FriendLoggedOutResponse frOutRes=(FriendLoggedOutResponse)update;
            User friend=DTOUtils.getFromDTO(frOutRes.getFriend());
            System.out.println("Friend logged out "+friend);
            try {
                client.friendLoggedOut(friend);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }

        if (update instanceof NewMessageResponse){
            NewMessageResponse msgRes=(NewMessageResponse)update;
            Message message=DTOUtils.getFromDTO(msgRes.getMessage());
            try {
                client.messageReceived(message);
            } catch (ChatException e) {
                e.printStackTrace();  
            }
        }
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse){
                         handleUpdate((UpdateResponse)response);
                    }else{
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
