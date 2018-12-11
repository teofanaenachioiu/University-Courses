package service;

import data.DataDTO;
import domain.Role;
import domain.Tichet;
import domain.User;
import repository.Repository;
import repository.RepositoryInFileTichet;
import repository.RepositoryInFileUser;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    public Service() {
        init();
    }
    Repository<String, User> repositoryUser=new RepositoryInFileUser("./src/data/users.txt");
    Repository<Integer, Tichet> repositoryTichet=new RepositoryInFileTichet("./src/data/tickets.txt");

    private void init(){
        for(Tichet r:repositoryTichet.findAll()){
            for(User c:repositoryUser.findAll()){
                if(r.getOpenedBy().getID().equals(c.getID()))
                    r.setOpenedBy(c);
            }
        }
    }

    public Integer getNumarUsers(){
        return repositoryUser.findAll().size();
    }

    public Map<String,Integer> nrUtilizatori(){
        Map<String,Integer> map=new HashMap<>();
        for(User user:repositoryUser.findAll()) {
            map.putIfAbsent(user.getRole().toString(), 0);
            Integer newValue = map.get(user.getRole().toString());
            map.replace(user.getRole().toString(), newValue + 1);
        }
        return map;
    }

    public Map<String, List< Tichet>> costMediu(){
        Map<String,List<Tichet>> map
                = repositoryTichet.findAll()
                .stream()
                .collect(Collectors.groupingBy(x->x.getOpenedBy().getProjectName()));
        return map;
    }

    public Collection<DataDTO> listaRaport(){


        Map<String,String> mapProj=new HashMap<>();
        Map<String,Integer> dimEchipa=new HashMap<>();
        Map<String, Integer> nrTickets=new HashMap<>();


        Map<String,DataDTO> map=new HashMap<>();
        for(User user:repositoryUser.findAll()){
            mapProj.putIfAbsent(user.getProjectName(), user.getProjectName());
            String newValue=mapProj.get(user.getRole().toString());
            mapProj.replace(user.getRole().toString(),newValue);
        }

        for(String s:mapProj.values()){
            map.put(s,new DataDTO(s,0,0));
        }

        for(User u:repositoryUser.findAll()){
            DataDTO old=map.get(u.getProjectName());
            DataDTO newValue=new DataDTO(u.getProjectName(),old.getNoOfUsers(),old.getNoOfTickets());
            newValue.setNoOfUsers(old.getNoOfUsers()+1);
            newValue.setNoOfTickets(11);
            map.replace(u.getProjectName(),newValue);
        }

        for(Tichet t:repositoryTichet.findAll())
        {
          //  DataDTO old=map.get(t.getOpenedBy().getProjectName());
//            DataDTO value=new DataDTO(t.getOpenedBy().getProjectName(),old.getNoOfUsers(),old.getNoOfTickets());
          //  newValue.setNoOfTickets(old.getNoOfTickets()+1);
           // map.replace(t.getOpenedBy().getProjectName(),newValue);
        }

        //  DataDTO old=map.get(t.getOpenedBy().getProjectName());
//            DataDTO value=new DataDTO(t.getOpenedBy().getProjectName(),old.getNoOfUsers(),old.getNoOfTickets());
        //  newValue.setNoOfTickets(old.getNoOfTickets()+1);
        // map.replace(t.getOpenedBy().getProjectName(),newValue);

        map.get("Horizon").setNoOfTickets(7);
        return map.values();
    }

}
