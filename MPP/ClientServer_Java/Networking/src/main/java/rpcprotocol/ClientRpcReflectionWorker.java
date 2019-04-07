package rpcprotocol;

import model.Proba;
import model.User;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ClientRpcReflectionWorker implements Runnable, IObserver {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();

    public ClientRpcReflectionWorker(IServer server, Socket connection) {
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
            } catch (IOException | ClassNotFoundException e) {
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

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        User user=(User)request.data();
        try {
            server.login(user.getID(),user.getHash(), this);
            return okResponse;
        } catch (MyAppException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleCAUTA_USERNAME(Request request){
        System.out.println("Cauta username Request ...");
        String username=(String)request.data();
        try{
            User user=server.cauta(username);
            return new Response.Builder().type(ResponseType.GET_USER).data(user).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        User user=(User)request.data();
        try {
            server.logout(user);
            connected=false;
            return okResponse;

        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleNR_PARTICIPANTI_PROBA(Request request){
        System.out.println("Nr participanti proba Request ...");
        Proba proba=(Proba)request.data();
        try {
            int nrProba=(int)server.nrParticipantiProba(proba);
            return new Response.Builder().type(ResponseType.GET_NR_PARTICIPANTI_PROBA).data(nrProba).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_PROBE(Request request){
        System.out.println("Lista probe Request ...");
        try {
            Iterable<Proba> pr= server.listaProbe();
            int nr=0;
            for (Proba ignored : pr){
                nr+=1;
            }
            Proba[] probe=new Proba[nr];
            int i=0;
            for (Proba p : pr){
                probe[i]=p;
            }
            return new Response.Builder().type(ResponseType.GET_LISTA_PROBE).data(probe).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
