package rpcprotocol;

import dto.InscriereDTO;
import dto.UtilsDTO;
import model.*;
import model.dto.DTOutils;
import model.dto.ProbaDTO;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
                Thread.sleep(3000);
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

    private Response handleLISTA_PROBE_DTO(Request request){
        System.out.println("Lista probe Request ...");
        try {
            ProbaDTO[] probaDTO= server.listaProbeDTO();
            return new Response.Builder().type(ResponseType.GET_LISTA_PROBE_DTO).data(probaDTO).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_PARTICIPANTI(Request request){
        System.out.println("Lista participanti Request ...");
        try {
            Iterable<Participant>  parts= server.listaParticipanti();
            int nr = 0;
            for (Participant ignored : parts)
                nr+=1;
            Participant[] participants = new Participant[nr];
            nr=0;
            for (Participant p: parts){
                participants[nr]=p;
                nr+=1;
            }

            return new Response.Builder().type(ResponseType.GET_LISTA_PARTICIPANTI).data(participants).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_PARTICIPANTI_FILTRATI(Request request){
        String [] keywords=(String[]) request.data();
        System.out.println("Lista participanti Request ...");
        try {
            Iterable<Participant>  parts= server.filtreazaParticipantiKeyword(keywords[0],keywords[1]);
            int nr = 0;
            for (Participant ignored : parts)
                nr+=1;
            Participant[] participants = new Participant[nr];
            nr=0;
            for (Participant p: parts){
                participants[nr]=p;
                nr+=1;
            }

            return new Response.Builder().type(ResponseType.GET_LISTA_PARTICIPANTI_FILTRATI).data(participants).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleINSCRIERE_NOUA(Request request){
        InscriereDTO inscriereDTO=(InscriereDTO) request.data();
        System.out.println("Lista participanti Request ...");
        try {
            List<Proba> lista = new ArrayList<>(Arrays.asList(inscriereDTO.getProbe()));
            server.inscriereParticipant(inscriereDTO.getNume(),inscriereDTO.getVarsta(),
                    lista,inscriereDTO.getUsernameOperator());
//            return new Response.Builder().type(ResponseType.UPDATE).data(null).build();
            return new Response.Builder().type(ResponseType.OK).data(null).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_PROBE_NUME(Request request){
        System.out.println("Lista probe nume Request ...");
        try {
            Iterable<String>  parts= server.listaProbeNume();
            int nr = 0;
            for (String ignored : parts)
                nr+=1;
            String[] probe = new String[nr];
            nr=0;
            for (String p: parts){
                probe[nr]=p;
                nr+=1;
            }

            return new Response.Builder().type(ResponseType.GET_LISTA_PROBE_NUME).data(probe).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_CATEGORII(Request request){
        System.out.println("Lista categorii Request ...");
        try {
            Iterable<Categorie>  parts= server.listaCategorii();
            int nr = 0;
            for (Categorie ignored : parts)
                nr+=1;
            Categorie[] categorii = new Categorie[nr];
            nr=0;
            for (Categorie p: parts){
                categorii[nr]=p;
                nr+=1;
            }

            return new Response.Builder().type(ResponseType.GET_LISTA_CATEGORII).data(categorii).build();
        } catch (MyAppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLISTA_PROBE(Request request){
        System.out.println("Lista probe Request ...");
        try {
            Iterable<Proba>  parts= server.listaProbe();
            int nr = 0;
            for (Proba ignored : parts)
                nr+=1;
            Proba[] probe = new Proba[nr];
            nr=0;
            for (Proba p: parts){
                probe[nr]=p;
                nr+=1;
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

    @Override
    public void update() throws MyAppException {
        Response resp=new Response.Builder().type(ResponseType.UPDATE).data(null).build();
        System.out.println("O noua inscriere din worker");
        try {
            sendResponse(resp);
            System.out.println("Dupa raspuns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
