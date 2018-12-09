package validation;

import domain.Entity2;
import repository.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorEntity2 implements Validator<Entity2> {
    private static Pattern idPtrn = Pattern.compile("^[1-9][0-9]{1,}$");
    @Override
    public void validate(Entity2 entity) {
        Matcher mtch =idPtrn.matcher(entity.getID().toString());
        if(!mtch.matches()){
            throw new ValidationException("Id incorect!");
        }
    }
}