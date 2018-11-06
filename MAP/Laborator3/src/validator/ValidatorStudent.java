package validator;

import domain.Student;
import repository.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clasa Validator Student
 * Validarea datelor de intrare pentru student
 */
public class ValidatorStudent implements Validator<Student> {
    private static Pattern usrNamePtrn = Pattern.compile("^[A-Za-z ,.'-]+$");
    private static Pattern usrIdPtrn = Pattern.compile("^[1-9][0-9]{1,}$");
    private static Pattern usrGrupaPtrn = Pattern.compile("^[1-9]{3}$");
    private static Pattern usrEmailPtrn = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    /**
     * Validarea numelui studentului
     * @param name - String (contine litere mari/mici si caracterele speciale: ,.'-)
     * @throws ValidationException daca numele nu e valid
     */
    private static void validateName(String name) {
        Matcher mtch = usrNamePtrn.matcher(name);
        if(!mtch.matches()){
            throw new ValidationException("Nume incorect!");
        }
    }

    /**
     * Validarea id-ului studentului
     * @param id - numar intreg >0
     * @throws ValidationException daca id-ul nu e valid
     */
    private static void validateID(String id){
        Matcher mtch = usrIdPtrn.matcher(id);
        if(!mtch.matches()){
            throw new ValidationException("Id incorect!");
        }
    }

    /**
     * Validarea id-ului studentului
     * @param grupa - numar intreg de 3 cifre
     * @throws ValidationException daca id-ul nu e valid
     */
    private static void validateGrupa(String grupa) {
        Matcher mtch = usrGrupaPtrn.matcher(grupa);
        if(!mtch.matches()){
            throw new ValidationException("Grupa incorecta!");
        }
    }

    /**
     * Validarea email-ului studentului
     * @param email - String
     * @throws ValidationException daca email-ul nu e valid
     */
    private static void validateEmail(String email)  {
        Matcher mtch = usrEmailPtrn.matcher(email);
        if(!mtch.matches()){
            throw new ValidationException("Email incorect!");
        }
    }

    /**
     * Validarea entitatii Student
     * @param entity - entitatea de validat
     */
    @Override
    public void validate(Student entity)  {
        validateID(entity.getID());
        validateName(entity.getNume());
        validateGrupa(entity.getGrupa());
        validateEmail(entity.getEmail());
        validateName(entity.getIndrumatorLab());
    }
}
