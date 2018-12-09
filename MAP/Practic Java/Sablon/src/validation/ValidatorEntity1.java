package validation;

import domain.Entity1;
import repository.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorEntity1 implements Validator<Entity1> {
    private static Pattern namePtrn = Pattern.compile("^[A-Za-z ,.'-]+$");
    @Override
    public void validate(Entity1 entity) {
        Matcher mtch = namePtrn.matcher(entity.getNume());
        if(!mtch.matches()){
            throw new ValidationException("Nume incorect!");
        }
    }
}
