package repository;

import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.util.Pair;
import validator.Validator;

public class NotaRepoInFile extends AbstractRepoInFile<Pair<String,String>, Nota>  {
    /**
     * Constructorul clasei
     *
     * @param fileName  - String (numele fisierului)
     * @param validator - Validator (clasa Validator, validare date)
     */
    public NotaRepoInFile(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    public Nota extractEntity(String line) {
        String[] parts=line.split("/");
        return new Nota(parts[0],parts[1],parts[2],parts[3]);

    }
}
