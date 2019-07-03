package com.practic.examen.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.dto.JocDto;
import com.practic.examen.dto.JucatorDto;
import com.practic.examen.dto.JucatorFinalDto;
import com.practic.examen.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/haveToStartNewGame")
    public ResponseEntity<?> canPlayGame() {
        return new ResponseEntity<Boolean>(mainService.verificaTrebuieCreatJocNou(), HttpStatus.OK);
    }

    @GetMapping(value = "/startJoc/{username}")
    public ResponseEntity<?> startGame(@PathVariable String username) {
        Joc joc = mainService.startJoc(username);
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/currGame/{username}")
    public ResponseEntity<?> currentGame(@PathVariable String username) {
        Joc joc = mainService.setJucatorNouLaJoculCurent(username);
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getJucator/{username}")
    public ResponseEntity<?> getJucator(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/getDatalii/{idJoc}/{username}")
    public ResponseEntity<?> getDatalii(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        return new ResponseEntity<>(convertToJucatorFinalDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getAdversar/{username}")
    public ResponseEntity<?> getAdversar(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getAdversar(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/getTraseu/{idJoc}")
    public ResponseEntity<?> getTraseu(@PathVariable Integer idJoc) {
        String traseu = mainService.getTraseuJoc(idJoc);
        return new ResponseEntity<>(traseu, HttpStatus.OK);
    }

    @PostMapping(value = "/mutare")
    public ResponseEntity<?> mutareJucator (@RequestBody ObjectNode objectNode){
        Integer idJucator = Integer.parseInt(objectNode.get("id").asText());
        String numereGenerate = objectNode.get("numereGenerate").asText();
        Jucator jucator = Jucator.builder()
                .numereGenerate(numereGenerate)
                .build();
        jucator.setId(idJucator);
        mainService.mutareJucator(jucator);
        return new ResponseEntity<>("s-a mutat", HttpStatus.OK);
    }












    /*****************************************************************
     *                            DTOs                               *
     *****************************************************************/

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                .muta(jucator.isMuta())
                .username(jucator.getUsername())
                .numereGenerate(jucator.getNumereGenerate())
                .pozitii(jucator.getPozitii())
                .litera(jucator.getLitera())
                .build();
    }
    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .stareJoc(joc.getStareJoc())
                .nrMutari(joc.getNrMutari())
                .traseu(joc.getTraseu())
                .build();
    }

    private JucatorFinalDto convertToJucatorFinalDto(Jucator jucator){
        return JucatorFinalDto.builder()
                .numereGenerate(jucator.getNumereGenerate())
                .pozitii(jucator.getPozitii())
                .username(jucator.getUsername())
                .build();
    }
}
