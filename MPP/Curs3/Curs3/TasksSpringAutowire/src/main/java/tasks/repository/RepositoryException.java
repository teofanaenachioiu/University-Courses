package tasks.repository;

/**
 * Created by grigo on 11/14/16.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Exception ex) {
        super(ex);
    }
}