package curs.sockets.server;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 11:44:51 AM
 */
public class ServerException extends Exception{
    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);    
    }
}
