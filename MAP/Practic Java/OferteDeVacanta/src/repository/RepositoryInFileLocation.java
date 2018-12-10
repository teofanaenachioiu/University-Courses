package repository;

import domain.Location;

public class RepositoryInFileLocation extends AbstractRepositoryInFile<String, Location> {
    public RepositoryInFileLocation(String fileName) {
        super(fileName);
    }

    @Override
    public Location extractEntity(String line) {
        String[] parts=line.split("[,]");
        return new Location(parts[0],parts[1],parts[2],Integer.valueOf(parts[3]),Double.valueOf(parts[4]));
    }
}
