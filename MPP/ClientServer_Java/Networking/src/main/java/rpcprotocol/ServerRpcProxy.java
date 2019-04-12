package rpcprotocol;

import dto.InscriereDTO;
import javafx.application.Platform;
import model.Categorie;
import model.Participant;
import model.dto.ProbaDTO;
import model.Proba;
import model.User;
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServerRpcProxy implements IServer {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }


    @Override
    public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe, String usernameOperator) throws MyAppException {
        Proba[] probas=new Proba[listaProbe.size()];
        for(int i=0;i<listaProbe.size();i++){
            probas[i]=listaProbe.get(i);
        }
        InscriereDTO inscriereDTO = new InscriereDTO(nume,varsta,probas,usernameOperator);
        Request req=new Request.Builder().type(RequestType.INSCRIERE_NOUA).data(inscriereDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err=response.data().toString();
            //closeConnection();
            throw new MyAppException(err);
        }
    }

    @Override
    public Iterable<Participant> filtreazaParticipantiKeyword(String proba, String categorie) throws MyAppException {
        String[] keykords=new String [2];
        keykords[0]=proba;
        keykords[1]=categorie;
        Request req=new Request.Builder().type(RequestType.LISTA_PARTICIPANTI_FILTRATI).data(keykords).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        Participant[] participants=(Participant[]) response.data();

        return new ArrayList<>(Arrays.asList(participants));
    }

    @Override
    public Iterable<Proba> listaProbe() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_PROBE).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        Proba[] probas= (Proba[]) response.data();
        return Arrays.asList(probas);
    }

    @Override
    public ProbaDTO[] listaProbeDTO() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_PROBE_DTO).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        return (ProbaDTO[])response.data();
    }

    @Override
    public Iterable<Participant> listaParticipanti() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_PARTICIPANTI).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        Participant[] participants=(Participant[]) response.data();

        return new ArrayList<>(Arrays.asList(participants));
    }

    @Override
    public Iterable<Categorie> listaCategorii() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_CATEGORII).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        Categorie[] categories=(Categorie[]) response.data();

        return new ArrayList<>(Arrays.asList(categories));
    }

    @Override
    public Iterable<String> listaProbeNume() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_PROBE_NUME).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        String[] probe=(String[]) response.data();

        return new ArrayList<>(Arrays.asList(probe));
    }

    @Override
    public int nrParticipantiProba(Proba proba) throws MyAppException{
        Request req = new Request.Builder().type(RequestType.NR_PARTICIPANTI_PROBA).data(proba).build();
        sendRequest(req);
        Response response = readResponse();
        return (int) response.data();
    }

    @Override
    public User cauta(String username) throws MyAppException {
        Request req = new Request.Builder().type(RequestType.CAUTA_USERNAME).data(username).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new MyAppException(err);
        }
        return (User) response.data();
    }

    @Override
    public void login(String username, String password, IObserver client) throws MyAppException {
        initializeConnection();
        User user = new User(username, password); //o sa fie momentan admin...
        Request req = new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
        }
        if (response.type() == ResponseType.ERROR) {
            String err=response.data().toString();
            closeConnection();
            throw new MyAppException(err);
        }
    }

    @Override
    public void logout(User user) throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
    }

    private void closeConnection() {
        System.out.println("Inchidem conexiunea");
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws MyAppException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyAppException("Error sending object " + e);
        }

    }

    private Response readResponse() throws MyAppException {
        Response response = null;
        try {
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws MyAppException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.UPDATE) {
            System.out.println("Inscriere noua. Se notifica toti clientii.");

                System.out.println("Am intrat in handleUpdate din Proxi");
                Platform.runLater(()-> {
                    try {
                        System.out.println("Incerc updateurile!!!!!!!!!!!");
                        client.update();
                    } catch (MyAppException e) {
                        e.printStackTrace();
                    }
                });
                System.out.println("Am iesit in handleUpdate din Proxi");

        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        Platform.runLater(() -> handleUpdate((Response) response));
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                   // Thread.sleep(100);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
