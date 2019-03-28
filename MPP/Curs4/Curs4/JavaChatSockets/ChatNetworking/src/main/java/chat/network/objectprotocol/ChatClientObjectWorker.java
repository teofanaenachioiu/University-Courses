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


public class ChatClientObjectWorker implements Runnable, IChatObserver {
    private IChatServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientObjectWorker(IChatServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if (response!=null){
                   sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    public void messageReceived(Message message) throws ChatException {
        MessageDTO mdto= DTOUtils.getDTO(message);
        System.out.println("Message received  "+message);
        try {
            sendResponse(new NewMessageResponse(mdto));
        } catch (IOException e) {
            throw new ChatException("Sending error: "+e);
        }
    }

    public void friendLoggedIn(User friend) throws ChatException {
        UserDTO udto= DTOUtils.getDTO(friend);
        System.out.println("Friend logged in "+friend);
        try {
            sendResponse(new FriendLoggedInResponse(udto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void friendLoggedOut(User friend) throws ChatException {
        UserDTO udto= DTOUtils.getDTO(friend);
        System.out.println("Friend logged out "+friend);
        try {
            sendResponse(new FriendLoggedOutResponse(udto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.login(user, this);
                return new OkResponse();
            } catch (ChatException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.logout(user, this);
                connected=false;
                return new OkResponse();

            } catch (ChatException e) {
               return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof SendMessageRequest){
            System.out.println("SendMessageRequest ...");
            SendMessageRequest senReq=(SendMessageRequest)request;
            MessageDTO mdto=senReq.getMessage();
            Message message= DTOUtils.getFromDTO(mdto);
            try {
                server.sendMessage(message);
                 return new OkResponse();
            } catch (ChatException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetLoggedFriendsRequest){
            System.out.println("GetLoggedFriends Request ...");
            GetLoggedFriendsRequest getReq=(GetLoggedFriendsRequest)request;
            UserDTO udto=getReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                User[] friends=server.getLoggedFriends(user);
                UserDTO[] frDTO= DTOUtils.getDTO(friends);
                return new GetLoggedFriendsResponse(frDTO);
            } catch (ChatException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
