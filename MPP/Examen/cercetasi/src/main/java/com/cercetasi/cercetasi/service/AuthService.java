package com.cercetasi.cercetasi.service;

import com.cercetasi.cercetasi.domain.Coordonator;
import com.cercetasi.cercetasi.dto.CoordonatorDto;
import com.cercetasi.cercetasi.repository.CoordonatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private CoordonatorRepository coordonatorRepository;

    @Autowired
    AuthService(CoordonatorRepository coordonatorRepository) {
        this.coordonatorRepository = coordonatorRepository;
    }

    public CoordonatorDto loginCoordonator(Coordonator coordonator) throws Exception {
        Optional<Coordonator> optional = coordonatorRepository.findById(coordonator.getUsername());

        if(optional.isPresent()){
            Coordonator coordFound = optional.get();
            if(! coordFound.getPassword().equals(coordonator.getPassword())) {
                throw new Exception("Parola gresita!");
            }
            return coordonatorDtoConverter(coordFound);
        }
        else
            throw new Exception("Username gresit!");
    }

    public void logoutCoordonator(Coordonator coordonator) throws Exception {
        Optional<Coordonator> coordFound = coordonatorRepository.findById(coordonator.getUsername());
        if(!coordFound.isPresent()){
            throw new Exception("No user found");
        }
    }

    private CoordonatorDto coordonatorDtoConverter(Coordonator coordonator) {
        return CoordonatorDto.builder()
                .username(coordonator.getUsername())
                .password(coordonator.getPassword())
                .punctControl(coordonator.getPunctControl())
                .build();
    }
}
