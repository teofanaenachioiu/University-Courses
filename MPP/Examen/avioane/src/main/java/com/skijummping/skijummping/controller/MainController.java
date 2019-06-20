package com.skijummping.skijummping.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skijummping.skijummping.domain.Joc;
import com.skijummping.skijummping.domain.Jucator;
import com.skijummping.skijummping.dto.JocDto;
import com.skijummping.skijummping.dto.JocFinalDto;
import com.skijummping.skijummping.dto.JucatorDto;
import com.skijummping.skijummping.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avioane")
public class MainController {
    MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping(value = "/startJoc")
    public ResponseEntity<?> startJoc(@RequestBody ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        Integer pozitieAvion = Integer.parseInt(objectNode.get("pozitieAvion").asText());
        Jucator jucator = Jucator.builder()
                .pozitieAvion(pozitieAvion)
                .username(username)
                .build();
        Joc joc = mainService.startJoc(jucator);
        return new ResponseEntity<>(this.convertJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/juc/{idJoc}/{username}")
    public ResponseEntity<?> getJucatorCurent(@PathVariable Integer idJoc, @PathVariable String username){
        return new ResponseEntity<>(convertJucatorDto(this.mainService.getJucatorByUsername(idJoc,username)),HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getJocuriJucator(@PathVariable String username){
        List<Joc> jocs = this.mainService.listaJocuriJucator(username);
        List<JocFinalDto> lista = new ArrayList<>();
        for(Joc joc: jocs){
            lista.add(convertFinalJoc(joc));
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/jocuri/{idJucator}")
    public ResponseEntity<?> setCastigator(@PathVariable Integer idJoc, @PathVariable Integer idJucator){
        mainService.setCastigator(idJoc,idJucator);
        return new ResponseEntity<>("castigator setat", HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/muta")
    public ResponseEntity<?> muta(@PathVariable Integer idJoc){
        return new ResponseEntity<>(convertJucatorDto(mainService.getJucatorCareMuta(idJoc)), HttpStatus.OK);
    }

    @GetMapping(value = "/juc/{idJoc}/{username}/adversar")
    public ResponseEntity<?> getAdversar (@PathVariable Integer idJoc, @PathVariable String username){
        return new ResponseEntity<>(convertJucatorDto(this.mainService.getAdversar(idJoc,username)),HttpStatus.OK);
    }

    private JocFinalDto convertFinalJoc(Joc joc){
        return JocFinalDto.builder()
                .id(joc.getId())
                .pozitieAvion1(joc.getJucatori().get(0).getPozitieAvion())
                .pozitieAvion2(joc.getJucatori().get(1).getPozitieAvion())
                .usernameJucator1(joc.getJucatori().get(0).getUsername())
                .usernameJucator2(joc.getJucatori().get(1).getUsername())
                .catigator(joc.getJucatori().get(0).isCastigator()? joc.getJucatori().get(0).getUsername() :
                        joc.getJucatori().get(1).getUsername())
                .build();

    }

    private JocDto convertJocDto(Joc joc) {
        return JocDto.builder()
                .stareJoc(joc.getStareJoc())
                .id(joc.getId())
                .build();
    }

    private JucatorDto convertJucatorDto(Jucator jucator){
        return JucatorDto.builder()
                .id(jucator.getId())
                .username(jucator.getUsername())
                .castigator(jucator.isCastigator())
                .muta(jucator.isMuta())
                .pozitieAvion(jucator.getPozitieAvion())
                .build();
    }
}
