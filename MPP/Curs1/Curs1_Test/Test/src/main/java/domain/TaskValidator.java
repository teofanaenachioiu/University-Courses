package domain;

import javax.xml.validation.*;

public class TaskValidator implements Validator<Task>{

    @Override
    public void validate(Task entity) throws ValidationException {
        String errMsg="";

        if (errMsg!="")
            throw new ValidationException(errMsg);
    }
}
