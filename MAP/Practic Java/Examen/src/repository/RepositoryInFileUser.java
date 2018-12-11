package repository;

import domain.Role;
import domain.User;

public class RepositoryInFileUser extends AbstractRepositoryInFile<String, User> {
    public RepositoryInFileUser(String fileName) {
        super(fileName);
    }

    @Override
    public User extractEntity(String line) {
        String[] parts=line.split("[,]");
        return new User(parts[0],Role.valueOf(parts[1]),parts[2]);
    }
}