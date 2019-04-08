package rpcprotocol;

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
    public Iterable<Proba> listaProbe() throws MyAppException {
        Request req=new Request.Builder().type(RequestType.LISTA_PROBE).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyAppException(err);
        }
        Proba[] pr=(Proba[])response.data();
        return Arrays.asList(pr);
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
        if (response.type() == ResponseType.INSCRIERE_NOUA) {

//            User friend=DTOUtils.getFromDTO((UserDTO) response.data());
//            System.out.println("Friend logged in "+friend);
//            try {
//                client.friendLoggedIn(friend);
//            } catch (ChatException e) {
//                e.printStackTrace();
//            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.INSCRIERE_NOUA;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                System.out.println("in while");
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Thread.sleep(100);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
