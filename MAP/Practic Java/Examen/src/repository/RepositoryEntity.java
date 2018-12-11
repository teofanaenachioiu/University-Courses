package repository;

import domain.Entity;

public class RepositoryEntity extends AbstractRepositoryInFile<Integer, Entity> {
    public RepositoryEntity(String fileName) {
        super(fileName);
    }

    @Override
    public Entity extractEntity(String line) {
        String[] parts=line.split("[,]");
        return new Entity(Integer.valueOf(parts[0]),parts[1]);
    }
}