package repository;

public class RepositoryInFileEntity12 {
}

//public class RepositoryInFileReservation extends AbstractRepositoryInFile<Pair<Client, Location>, Reservation> {
//
//    public RepositoryInFileReservation(String fileName) {
//        super(fileName);
//    }
//
//    @Override
//    public Reservation extractEntity(String line) {
//        String[] parts=line.split("[,]");
//        Client client=new Client();
//
//        Location location=new Location();
//
//        DateTimeFormatter formatter=Reservation.getDateFormatter();
//        LocalDateTime date= LocalDateTime.parse(parts[2],formatter);
//
//        return new Reservation(client,location, date,Integer.valueOf(parts[3]));
//    }
//}