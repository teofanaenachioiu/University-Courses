package sem4.repository;

import sem4.domain.MessageTask;
import sem4.domain.validator.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class InFileMessageTaskRepository extends AbstractFileRepository<String, MessageTask> {


    public InFileMessageTaskRepository(String fileName, Validator<MessageTask> validator) {
        super(fileName, validator);
    }

    @Override
    public MessageTask extractEntity(List<String> attr) {

       //return Messages.createMessageTask(attributes);
        String id=attr.get(0).split("=")[1];
        String desc=attr.get(1).split("=")[1];
        String msg=attr.get(2).split("=")[1];
        String from=attr.get(3).split("=")[1];
        String to=attr.get(4).split("=")[1];
        String dateAsString=attr.get(5).split("=")[1];
        LocalDateTime date= LocalDateTime.parse(dateAsString,MessageTask.getDateFormatter());
        MessageTask t=new MessageTask(id,desc,msg,from,to,date);
        return t;
    }


}


