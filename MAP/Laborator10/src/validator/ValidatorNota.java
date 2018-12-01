package validator;

import domain.Nota;
import repository.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorNota implements Validator<Nota> {
//    private static Pattern idPtrn = Pattern.compile("^[1-9][0-9]*$");
    private static Pattern datePtrn = Pattern.compile("^[1-9]|1[0-4]$");
    //private static Pattern notaPtrn = Pattern.compile("^[1-9]|10$");

//    private void validateId(String id){
//        Matcher mtch = idPtrn.matcher(id);
//        if(!mtch.matches()){
//            throw new ValidationException("Id incorect!");
//        }
//    }

    private void validateDate(Integer data){
        String dataS=data.toString();
        Matcher mtch = datePtrn.matcher(dataS);
        if(!mtch.matches()){
            throw new ValidationException("Data incorecta!");
        }
    }

    private void validateNota(Float nota){
        //String dataS=data.toString();
        //Matcher mtch = notaPtrn.matcher(dataS);
        //if(!mtch.matches()){
        if(nota<1 || nota>10)
            throw new ValidationException("Data incorecta!");

    }
    @Override
    public void validate(Nota entity) throws ValidationException {
        validateNota(entity.getNotaProf());
        validateDate(Integer.parseInt(entity.getDataCurenta()));
    }
}
