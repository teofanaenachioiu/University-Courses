package repository;

import domain.Client;
import domain.Location;
import domain.Reservation;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepositoryInFileReservation extends AbstractRepositoryInFile<Pair<Client, Location>, Reservation> {

    public RepositoryInFileReservation(String fileName) {
        super(fileName);
    }

    @Override
    public Reservation extractEntity(String line) {
        String[] parts=line.split("[,]");
        Client client=new Client(Long.valueOf( parts[0]),"ceva");
//        for (Client c:listaClienti
//             ) {
//            if(c.getID()==Long.valueOf(parts[0]))
//                client=c;
//        }

        Location location=new Location(parts[1],"nume","hotel",0,0);
//        for (Location l:listaLocatii
//             ) {
//            if(l.getID().equals(parts[1]))
//                location=l;
//        }
        DateTimeFormatter formatter=Reservation.getDateFormatter();
        LocalDateTime date= LocalDateTime.parse(parts[2],formatter);

        return new Reservation(client,location, date,Integer.valueOf(parts[3]));
    }
}
