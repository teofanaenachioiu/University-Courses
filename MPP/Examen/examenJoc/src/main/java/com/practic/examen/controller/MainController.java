package com.practic.examen.controller;

import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.dto.JocDto;
import com.practic.examen.dto.JucatorDto;
import com.practic.examen.dto.JucatorFinalDto;
import com.practic.examen.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joc")
public class MainController {
    private MainService mainService;

    @Autowired
    MainController(MainService mainService){
        this.mainService = mainService;
    }

    /*****************************************************************
     *                          Servicii REST                        *
     *****************************************************************/

    @GetMapping(value = "/canPlay")
    public ResponseEntity<?> canPlayGame() {
        return new ResponseEntity<Boolean>(mainService.canPlay(), HttpStatus.OK);
    }

    @GetMapping(value = "/startJoc")
    public ResponseEntity<?> startGame() {
        Joc joc = mainService.startJoc();
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/currGame")
    public ResponseEntity<?> currentGame() {
        Joc joc = mainService.curentGame();
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getJucator/{username}")
    public ResponseEntity<?> getJucator(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }














    /*****************************************************************
     *                            DTOs                               *
     *****************************************************************/

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                // adauga restul campurilor !!!!!!!!!!!!!!!!!!!
                .build();
    }
    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .stareJoc(joc.getStareJoc())
                // adauga restul campurilor !!!!!!!!!!!!!!!!!!!
                .build();
    }

    private JucatorFinalDto convertToJucatorFinalDto(Jucator jucator){
        return JucatorFinalDto.builder()
                // adauga restul campurilor !!!!!!!!!!!!!!!!!!!
                .build();
    }
}
