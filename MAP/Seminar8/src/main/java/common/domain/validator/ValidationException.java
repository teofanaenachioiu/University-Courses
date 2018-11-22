package common.domain.validator;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }
}
