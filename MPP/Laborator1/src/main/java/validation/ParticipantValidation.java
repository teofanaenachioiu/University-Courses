package validation;

import model.Participant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticipantValidation implements Validator<Participant> {
    private static Pattern namePattern = Pattern.compile("^[A-Za-z ,.'-]+$");
    private static Pattern varstaPattern = Pattern.compile("[8-9]|1[0-5]");

    @Override
    public void validate(Participant entity) throws ValidationException {
        Matcher mtch = namePattern.matcher(entity.getNume());
        if(!mtch.matches()){
            throw new ValidationException("Nume invalid!");
        }
        mtch=varstaPattern.matcher(entity.getVarsta().toString());
        if(!mtch.matches()){
            throw new ValidationException("Varsta invalida!");
        }
        if(entity.getVarsta()<8){
            throw  new ValidationException("Participantul are o varsta prea mica");
        }
        if(entity.getVarsta()>15){
            throw  new ValidationException("Participantul are varsta prea mare");
        }
    }
}
