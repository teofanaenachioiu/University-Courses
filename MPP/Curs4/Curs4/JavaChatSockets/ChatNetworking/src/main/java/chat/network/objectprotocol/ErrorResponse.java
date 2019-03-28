package chat.network.objectprotocol;


public class ErrorResponse implements Response {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
