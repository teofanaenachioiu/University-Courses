package curs.sockets.server;


public class StartServer {
    public static void main(String[] args) {
      // AbstractServer server=new SequentialServer(55555);
       //AbstractServer server=new MyConcurrentServer(55555);
      AbstractServer server=new SerialConcurrentServer(55555);
        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
