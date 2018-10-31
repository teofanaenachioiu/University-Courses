package repository;

import domain.Student;
import validator.Validator;

public class StudentRepoInFile extends AbstractRepoInFile<String, Student> {

    /**
     * Constructorul clasei
     * @param fileName - String (numele fisierului)
     * @param validator - clasa Validator (validarea datelor)
     */
    public StudentRepoInFile(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }
    /**
     * Extrage datele dintr-un string si creeaza entitatea Student
     * @param line - String
     * @return entitate Student nou creata
     */
    @Override
    public Student extractEntity(String line) {
        String[] parts=line.split("/");
        return new Student(parts[0],parts[1],parts[2],parts[3],parts[4]);
    }
}
