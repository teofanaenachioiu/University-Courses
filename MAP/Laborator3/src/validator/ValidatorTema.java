package validator;

import domain.Tema;
import repository.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorTema implements Validator<Tema> {
    private static Pattern idPtrn = Pattern.compile("^[1-9][0-9]*$");
    private static Pattern datePtrn = Pattern.compile("^[1-9]|1[0-4]$");

    private void validateId(Integer id){
        String idS=id.toString();
        Matcher mtch = idPtrn.matcher(idS);
        if(!mtch.matches()){
            throw new ValidationException("Id incorect!");
        }
    }

    private void validateDate(Integer data){
        String dataS=data.toString();
        Matcher mtch = datePtrn.matcher(dataS);
        if(!mtch.matches()){
            throw new ValidationException("Data incorecta!");
        }
    }

    private void validateInterval(Integer predare,Integer deadline){
        if(predare>deadline)
            throw  new ValidationException("Date incorecte!");
    }


    @Override
    public void validate(Tema entity) throws ValidationException {
        validateId(entity.getID());
        validateDate(entity.getDeadline());
        validateDate(entity.getDataPredare());
        validateInterval(entity.getDataPredare(),entity.getDeadline());
    }
}
