package repository;

import domain.Role;
import domain.Tichet;
import domain.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RepositoryInFileTichet extends AbstractRepositoryInFile<Integer, Tichet> {
    //private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public RepositoryInFileTichet(String fileName) {
        super(fileName);
    }

    @Override
    public Tichet extractEntity(String line) {
        String[] parts=line.split("[,]");
        User user=new User(parts[2], Role.developer,"projj");
        String datttt=parts[3];
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");





        LocalDate localDate = LocalDate.parse(datttt,formatter);
        return new Tichet(Integer.valueOf(parts[0]),parts[1],user,localDate,Double.valueOf(parts[4]));
    }
}
