package domain;

import domain.MessageTask;
import domain.ValidationException;
import domain.Validator;

/**
 * Created by camelia on 10/16/2017.
 */
public class MessageTaskValidator  implements Validator<MessageTask> {
    @Override
    public void validate(MessageTask entity) throws ValidationException {
        String errMsg="";

        if (errMsg!="")
            throw new ValidationException(errMsg);
    }
}
