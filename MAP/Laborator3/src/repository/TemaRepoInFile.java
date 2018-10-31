package repository;

import domain.Tema;
import validator.Validator;

public class TemaRepoInFile extends AbstractRepoInFile<String, Tema> {
    /**
     * Constructorul clasei
     * @param fileName - String (numele fisierului)
     * @param validator - clasa Validator (validarea datelor)
     */
    public TemaRepoInFile(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }
    /**
     * Extrage datele dintr-un string si creeaza entitatea Tema
     * @param line - String
     * @return entitate Tema nou creata
     */
    @Override
    public Tema extractEntity(String line) {
        String[] parts=line.split("/");
        return new Tema(parts[0],parts[1],parts[2],parts[3]);
    }
}
