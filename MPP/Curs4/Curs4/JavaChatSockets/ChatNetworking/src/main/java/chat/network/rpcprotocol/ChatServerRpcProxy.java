package chat.network.rpcprotocol;

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


public class ChatServerRpcProxy implements IChatServer {
    private String host;
    private int port;

    private IChatObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ChatServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(User user, IChatObserver client) throws ChatException {
        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ChatException(err);
        }
    }

    public void sendMessage(Message message) throws ChatException {
        MessageDTO mdto=DTOUtils.getDTO(message);
        Request req=new Request.Builder().type(RequestType.SEND_MESSAGE).data(mdto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
    }

    public void logout(User user, IChatObserver client) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
    }



    public User[] getLoggedFriends(User user) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.GET_LOGGED_FRIENDS).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
        UserDTO[] frDTO=(UserDTO[])response.data();
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


    private void handleUpdate(Response response){
        if (response.type()== ResponseType.FRIEND_LOGGED_IN){

            User friend=DTOUtils.getFromDTO((UserDTO) response.data());
            System.out.println("Friend logged in "+friend);
            try {
                client.friendLoggedIn(friend);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }
        if (response.type()== ResponseType.FRIEND_LOGGED_OUT){
            User friend=DTOUtils.getFromDTO((UserDTO)response.data());
            System.out.println("Friend logged out "+friend);
            try {
                client.friendLoggedOut(friend);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }

        if (response.type()== ResponseType.NEW_MESSAGE){
            Message message=DTOUtils.getFromDTO((MessageDTO)response.data());
            try {
                client.messageReceived(message);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.FRIEND_LOGGED_OUT || response.type()== ResponseType.FRIEND_LOGGED_IN || response.type()== ResponseType.NEW_MESSAGE;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

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
