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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/joc")
public class MainController {
    private MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /*****************************************************************
     *                          Servicii REST                        *
     *****************************************************************/

    @GetMapping(value = "/haveToStartNewGame")
    public ResponseEntity<?> canPlayGame() {
        return new ResponseEntity<Boolean>(mainService.verificaTrebuieCreatJocNou(), HttpStatus.OK);
    }

    @PostMapping(value = "/startJoc")
    public ResponseEntity<?> startGame(@RequestBody ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        Integer pozitie1 = Integer.parseInt(objectNode.get("pozitie1").asText());
        Integer pozitie2 = Integer.parseInt(objectNode.get("pozitie2").asText());
        Jucator jucator = Jucator.builder()
                .username(username)
                .muta(true)
                .castigator(false)
                .nrIncercari(0)
                .pozitie1(pozitie1)
                .pozitie2(pozitie2)
                .pozitie1Ghicita(false)
                .pozitie2Ghicita(false)
                .build();
        Joc joc = mainService.startJoc(jucator);
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @PostMapping(value = "/currGame")
    public ResponseEntity<?> currentGame(@RequestBody ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        Integer pozitie1 = Integer.parseInt(objectNode.get("pozitie1").asText());
        Integer pozitie2 = Integer.parseInt(objectNode.get("pozitie2").asText());
        Jucator jucator = Jucator.builder()
                .username(username)
                .muta(false)
                .castigator(false)
                .nrIncercari(0)
                .pozitie1(pozitie1)
                .pozitie2(pozitie2)
                .pozitie1Ghicita(false)
                .pozitie2Ghicita(false)
                .build();
        Joc joc = mainService.setJucatorPentruJoculCurent(jucator);
        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getJucator/{username}")
    public ResponseEntity<?> getJucator(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getAdversar/{username}")
    public ResponseEntity<?> getAdversar(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getAdversar(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/ghiceste/{idJucator}/{pozitie}")
    public ResponseEntity<?> ghiciestePozitie(@PathVariable Integer idJoc, @PathVariable Integer idJucator,
                                              @PathVariable Integer pozitie) {
        boolean ghicit = mainService.verificaPozitie(pozitie, idJoc, idJucator);
        return new ResponseEntity<>(ghicit, HttpStatus.OK);

    }

    @GetMapping(value = "/detalii/{username}")
    public ResponseEntity<?> getDatalii(@PathVariable String username) {

        List<Joc> list = mainService.listaJocuriUtilizator(username);
        List<JucatorFinalDto> listaJocuri = new ArrayList<>();
        for (Joc joc : list) {
            listaJocuri.add(convertToJucatorFinalDto(joc, username));
        }
        return new ResponseEntity<>(listaJocuri, HttpStatus.OK);
    }

    /*****************************************************************
     *                            DTOs                               *
     *****************************************************************/

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                .username(jucator.getUsername())
                .muta(jucator.isMuta())
                .castigator(jucator.isCastigator())
                .nrIncercari(jucator.getNrIncercari())
                .pozitie1(jucator.getPozitie1())
                .pozitie2(jucator.getPozitie2())
                .pozitie1Ghicita(jucator.isPozitie1Ghicita())
                .pozitie2Ghicita(jucator.isPozitie2Ghicita())
                .build();
    }

    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .stareJoc(joc.getStareJoc())
                .build();
    }

    private JucatorFinalDto convertToJucatorFinalDto(Joc joc, String username) {
        Jucator jucator = joc.getJucatori().get(0).getUsername().equals(username) ?
                joc.getJucatori().get(0) : joc.getJucatori().get(1);
        Jucator adversar = joc.getJucatori().get(0).getUsername().equals(username) ?
                joc.getJucatori().get(1) : joc.getJucatori().get(0);

        String castigator = null;
        if (jucator.isCastigator())
            castigator = jucator.getUsername();
        if (adversar.isCastigator())
            castigator = jucator.getUsername();
        return JucatorFinalDto.builder()
                .idJoc(joc.getId())
                .usernameAdversar(adversar.getUsername())
                .usernameJucator(jucator.getUsername())
                .castigator(castigator)
                .idAdversar(adversar.getId())
                .idJucator(jucator.getId())
                .pozitie1Adversar(adversar.getPozitie1())
                .pozitie2Adversar(adversar.getPozitie2())
                .pozitie1Jucator(jucator.getPozitie1())
                .pozitie2Jucator(jucator.getPozitie2())
                .numarIncercariAdversar(adversar.getNrIncercari())
                .numarIncercariJucator(jucator.getNrIncercari())
                .build();
    }
}
