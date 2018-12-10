package domain;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//-	Clasa Reservation are atributele: client:Client, location:Location, startDate:LocalDateTime, noNights
public class Reservation implements HasID<Pair<Client,Location>>{
    private Client client;
    private Location location;
    private LocalDateTime startDate;
    private int noNights;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Reservation(Client client, Location location, LocalDateTime startDate, int noNights) {
        this.client = client;
        this.location = location;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }


    @Override
    public Pair<Client, Location> getID() {
        return new Pair<>(client,location);
    }

    @Override
    public void setID(Pair<Client, Location> clientLocationPair) {
        this.client=clientLocationPair.getKey();
        this.location=clientLocationPair.getValue();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getNoNights() {
        return noNights;
    }

    public void setNoNights(int noNights) {
        this.noNights = noNights;
    }

}
