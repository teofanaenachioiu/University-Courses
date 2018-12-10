package repository;

import domain.Client;

public class RepositoryInFileClient extends AbstractRepositoryInFile<Long, Client> {
    public RepositoryInFileClient(String fileName) {
        super(fileName);
    }

    @Override
    public Client extractEntity(String line) {
        String[] parts=line.split("[,]");
        return new Client(Long.valueOf(parts[0]),parts[1]);
    }
}
