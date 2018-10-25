package repository;

import domain.Student;
import validator.Validator;

public class StudentRepoInFile extends AbstractRepoInFile<Integer, Student> {

    public StudentRepoInFile(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    public Student extractEntity(String line) {
        String[] parts=line.split("/");
        return new Student(Integer.parseInt(parts[0]),parts[1],Integer.parseInt(parts[2]),parts[3],parts[4]);
    }
}
