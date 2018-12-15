package service;

import domain.Client;
import domain.Location;
import domain.Reservation;
import javafx.util.Pair;
import repository.Repository;
import repository.RepositoryInFileClient;
import repository.RepositoryInFileLocation;
import repository.RepositoryInFileReservation;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {
    Repository<Long, Client> repositoryClient=new RepositoryInFileClient("./src/data/client.txt");
    Repository<String, Location> repositoryLocation=new RepositoryInFileLocation("./src/data/location.txt");
    Repository<Pair<Client,Location>, Reservation> repositoryReservation
            =new RepositoryInFileReservation("./src/data/reservation.txt") ;

    public Service() {
        initReservations();
    }


    private void initReservations(){
        for(Reservation r:repositoryReservation.findAll()){
            for(Client c:repositoryClient.findAll()){
                if(r.getClient().getID().equals(c.getID()))
                    r.setClient(c);
            }
            for (Location l:repositoryLocation.findAll()){
                if(r.getLocation().getID().equals(l.getID()))
                    r.setLocation(l);
            }
        }
    }

    public ArrayList<Location> getLocatii(String locatie){
        //initReservations();
        ArrayList <Location> lista =new ArrayList<>();
        for(Reservation reservation:repositoryReservation.findAll()){
            if(reservation.getLocation().getLocationName().equals(locatie))
                lista.add(reservation.getLocation());
        }
        return lista;
    }



    public Map<String,Integer> numarRezervari(){
        //initReservations();
        Map<String,Integer> map=new HashMap<>();
        for(Reservation reservation:repositoryReservation.findAll()){
            if(reservation.getStartDate().getYear()== LocalDateTime.now().getYear()) {
                map.putIfAbsent(reservation.getClient().getName(), 0);
                Integer newValue=map.get(reservation.getClient().getName());
                map.replace(reservation.getClient().getName(),newValue+1);
            }
        }
        return map;
    }

    public Map<String,List< Reservation>> sumaIncasata(){
        Map<String,List<Reservation>> map
                = repositoryReservation.findAll()
                .stream()
                .collect(Collectors.groupingBy(x->x.getLocation().getHotel()));
        return map;
    }



}
