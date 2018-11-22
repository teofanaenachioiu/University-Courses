package common.domain.validator;


import common.domain.MessageTask;

public class MessageTaskValidator implements Validator<MessageTask> {

    @Override
    public void validate(MessageTask entity){
        String err="";
        //validate entity
        if (!err.equals(""))
            throw new ValidationException(err);

    }
}
