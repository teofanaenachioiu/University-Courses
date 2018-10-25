package repository;

import domain.Tema;
import validator.Validator;

public class TemaRepoInFile extends AbstractRepoInFile<Integer, Tema> {
    public TemaRepoInFile(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    public Tema extractEntity(String line) {
        String[] parts=line.split("/");
        return new Tema(Integer.parseInt(parts[0]),parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
    }
}
