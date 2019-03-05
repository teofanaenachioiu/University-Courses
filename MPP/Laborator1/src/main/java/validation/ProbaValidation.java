package validation;

import model.Proba;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProbaValidation implements Validator<Proba> {
    @Override
    public void validate(Proba entity) throws ValidationException {
        if(!entity.getCatg().toString().equals("CATEGORIE_6_8")
                || !entity.getCatg().toString().equals("CATEGORIE_9_11")
                || !entity.getCatg().toString().equals("CATEGORIE_12_15")){
            throw new ValidationException("Categorie de varsta invalida!");
        }
    }
}
