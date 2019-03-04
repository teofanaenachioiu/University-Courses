package tasks.model;

/**
 * Created by grigo on 1/18/17.
 */
public class TaskExecutionException extends RuntimeException {
    public TaskExecutionException() {
    }

    public TaskExecutionException(String message) {
        super(message);
    }

    public TaskExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskExecutionException(Throwable cause) {
        super(cause);
    }

    public TaskExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
