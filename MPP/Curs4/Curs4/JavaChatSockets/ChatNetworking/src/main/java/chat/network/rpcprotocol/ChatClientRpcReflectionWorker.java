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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ChatClientRpcReflectionWorker implements Runnable, IChatObserver {
    private IChatServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientRpcReflectionWorker(IChatServer server, Socket connection) {
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
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
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
        Response resp=new Response.Builder().type(ResponseType.NEW_MESSAGE).data(mdto).build();
        System.out.println("Message received  "+message);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new ChatException("Sending error: "+e);
        }
    }

    public void friendLoggedIn(User friend) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(friend);
        Response resp=new Response.Builder().type(ResponseType.FRIEND_LOGGED_IN).data(udto).build();
        System.out.println("Friend logged in "+friend);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void friendLoggedOut(User friend) throws ChatException {
        UserDTO udto=DTOUtils.getDTO(friend);
        Response resp=new Response.Builder().type(ResponseType.FRIEND_LOGGED_OUT).data(udto).build();
        System.out.println("Friend logged out "+friend);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        UserDTO udto=(UserDTO)request.data();
        User user=DTOUtils.getFromDTO(udto);
        try {
            server.login(user, this);
            return okResponse;
        } catch (ChatException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        UserDTO udto=(UserDTO)request.data();
        User user=DTOUtils.getFromDTO(udto);
        try {
            server.logout(user, this);
            connected=false;
            return okResponse;

        } catch (ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleSEND_MESSAGE(Request request){
            System.out.println("SendMessageRequest ...");
            MessageDTO mdto=(MessageDTO)request.data();
            Message message=DTOUtils.getFromDTO(mdto);
            try {
                server.sendMessage(message);
                return okResponse;
            } catch (ChatException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }


    }

    private Response handleGET_LOGGED_FRIENDS(Request request){
        System.out.println("GetLoggedFriends Request ...");
        UserDTO udto=(UserDTO)request.data();
        User user=DTOUtils.getFromDTO(udto);
        try {
            User[] friends=server.getLoggedFriends(user);
            UserDTO[] frDTO=DTOUtils.getDTO(friends);
            return new Response.Builder().type(ResponseType.GET_LOGGED_FRIENDS).data(frDTO).build();
        } catch (ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
