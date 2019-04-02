import repository.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Tema4\\Server\\src\\main\\resources\\db.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }


       AbstractServer server=new Server(55555);
       Repository repo=new Repository(prop);
      server.setData(repo);
        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
