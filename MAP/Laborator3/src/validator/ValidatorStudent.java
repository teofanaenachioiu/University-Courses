package validator;

import domain.Student;
import exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorStudent implements Validator<Student> {
    private static Pattern usrNamePtrn = Pattern.compile("^[A-Za-z ,.'-]+$");
    private static Pattern usrIdPtrn = Pattern.compile("^[1-9][0-9]{1,}$");
    private static Pattern usrGrupaPtrn = Pattern.compile("^[1-9]{3}$");
    private static Pattern usrEmailPtrn = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    public static void validateName(String name) throws ValidationException {
        Matcher mtch = usrNamePtrn.matcher(name);
        if(!mtch.matches()){
            throw new ValidationException("Nume incorect!");
        }
    }

    public static void validateID(Integer id) throws ValidationException {
        String idS=id.toString();
        Matcher mtch = usrIdPtrn.matcher(idS);
        if(!mtch.matches()){
            throw new ValidationException("Id incorect!");
        }
    }

    public static void validateGrupa(Integer grupa) throws ValidationException {
        String grupaS=grupa.toString();
        Matcher mtch = usrGrupaPtrn.matcher(grupaS);
        if(!mtch.matches()){
            throw new ValidationException("Grupa incorecta!");
        }
    }

    public static void validateEmail(String email) throws ValidationException {
        Matcher mtch = usrEmailPtrn.matcher(email);
        if(!mtch.matches()){
            throw new ValidationException("Email incorect!");
        }
    }

    @Override
    public void validate(Student entity) throws ValidationException {
        validateName(entity.getNume());
        validateID(entity.getID());
        validateGrupa(entity.getGrupa());
        validateEmail(entity.getEmail());
        validateName(entity.getIndrumatorLab());
    }
}
