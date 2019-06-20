package com.skijummping.skijummping.service;

import com.skijummping.skijummping.domain.Utilizator;
import com.skijummping.skijummping.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    AuthService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public Utilizator login(Utilizator utilizator) throws Exception {
        Optional<Utilizator> optional = utilizatorRepository.findById(utilizator.getUsername());

        if(optional.isPresent()){
            Utilizator utilizatorFound = optional.get();
            if(! utilizatorFound.getPassword().equals(utilizator.getPassword())) {
                throw new Exception("Parola gresita!");
            }
            utilizatorRepository.updateUtilizatorConnection(utilizator.getUsername(),true);
            utilizatorFound.setConectat(true);
            return utilizatorFound;
        }
        else
            throw new Exception("Username gresit!");
    }

    public void logout(Utilizator utilizator) throws Exception {
        Optional<Utilizator> coordFound = utilizatorRepository.findById(utilizator.getUsername());
        if(!coordFound.isPresent()){
            throw new Exception("No user found");
        }
        utilizatorRepository.updateUtilizatorConnection(utilizator.getUsername(),false);
    }


}
