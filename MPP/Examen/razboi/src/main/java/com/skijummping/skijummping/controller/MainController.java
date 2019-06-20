package com.skijummping.skijummping.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skijummping.skijummping.domain.Joc;
import com.skijummping.skijummping.domain.Jucator;
import com.skijummping.skijummping.dto.JocDto;
import com.skijummping.skijummping.dto.JucatorDto;
import com.skijummping.skijummping.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/razboi")
public class MainController {
    private MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(value = "/startJoc")
    public ResponseEntity<JocDto> startJoc() {
        return new ResponseEntity<>(convertToJocDto(mainService.incepeJoc()), HttpStatus.OK);
    }

    @GetMapping(value = "/canPlay")
    public ResponseEntity<Boolean> canPlay() {
        return new ResponseEntity<>(mainService.checkCanPlay(), HttpStatus.OK);
    }

//    @GetMapping(value = "/castigatorTura")
//    public ResponseEntity<List<String>> castigatorTura() {
//        return new ResponseEntity<>(mainService.getCastigatorTura(), HttpStatus.OK);
//    }

    @GetMapping(value = "/{idJoc}/getCarti")
    public ResponseEntity<List<String>> turaCompleta(@PathVariable String idJoc) {
        Integer id = Integer.parseInt(idJoc);
        return new ResponseEntity<>(mainService.getCarti(id), HttpStatus.OK);
    }

//    @GetMapping(value = "/{idJoc}/endGame")
//    public ResponseEntity<Boolean> endGame(@PathVariable String idJoc) {
//        Integer id = Integer.parseInt(idJoc);
//        return new ResponseEntity<>(mainService.checkEndGame(id), HttpStatus.OK);
//    }

    @GetMapping(value = "/{idJoc}/castigator")
    public ResponseEntity<List<JucatorDto>> getCastigatori(@PathVariable String idJoc) {
        Integer id = Integer.parseInt(idJoc);
        List<JucatorDto> jucatorDtoList = new ArrayList<>();
        for(Jucator jucator: mainService.getCastigatori(id) ){
            jucatorDtoList.add(convertToJucatorDto(jucator));
        }
        return new ResponseEntity<>(jucatorDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/jucatori")
    public ResponseEntity<List<JucatorDto>> getJocJucatori(@PathVariable String idJoc) {
        Integer id = Integer.parseInt(idJoc);
        List<JucatorDto> jucatorDtoList = new ArrayList<>();
        for (Jucator jucator : mainService.getJucatori(id)) {
            jucatorDtoList.add(convertToJucatorDto(jucator));
        }
        return new ResponseEntity<>(jucatorDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/depune")
    public ResponseEntity<?> depuneCarte(@RequestBody ObjectNode objectNode) {
        Integer idJoc = Integer.parseInt(objectNode.get("idJoc").asText());
        Integer idJucator = Integer.parseInt(objectNode.get("idJucator").asText());
        String denumireCarte = objectNode.get("denumireCarte").asText();
        this.mainService.depundeCarte(idJoc, idJucator, denumireCarte);
        return new ResponseEntity<>("s-a depus cartea " +denumireCarte, HttpStatus.OK);
    }

    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .mutariTura(joc.getMutariTura())
                .stareJoc(joc.getStareJoc())
                .build();
    }

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .username(jucator.getUsername())
                .password(jucator.getPassword())
                .castigator(jucator.isCastigator())
                .id(jucator.getId())
                .build();
    }

}
