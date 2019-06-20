package com.practic.examen.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.dto.JocDto;
import com.practic.examen.dto.JocFinalDto;
import com.practic.examen.dto.JucatorDto;
import com.practic.examen.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/anagrame")
public class MainController {
    private MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(value = "/startJoc")
    public ResponseEntity<?> incepeJocNou() {
        return new ResponseEntity<>(convertToJocDto(mainService.startJoc()), HttpStatus.OK);
    }

    @GetMapping(value = "/getLastGame")
    public ResponseEntity<?> ultimulJoc() {
        return new ResponseEntity<>(convertToJocDto(mainService.lastGame()), HttpStatus.OK);
    }

    @GetMapping(value = "/canPlay")
    public ResponseEntity<?> putJuca() {
        return new ResponseEntity<Boolean>(mainService.canPlay(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getJucatori")
    public ResponseEntity<?> getJucatori(@PathVariable Integer idJoc) {
        List<Jucator> jucatorList = mainService.getJucatori(idJoc);
        List<JucatorDto> jucatorDtoList = new ArrayList<>();
        for (Jucator jucator : jucatorList) {
            jucatorDtoList.add(convertToJucatorDto(jucator));
        }
        return new ResponseEntity<>(jucatorDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/jucatori/{username}")
    public ResponseEntity<?> getDetalii(@PathVariable String username) {
        List<Joc> jocList = mainService.getJocuri(username);
        List<JocFinalDto> jocFinalDtoDtos = new ArrayList<>();
        for (Joc joc : jocList) {
            Jucator jucatorCastigator = mainService.getCastigator(joc);
            jocFinalDtoDtos.add(convertToJocFinalDto(joc,jucatorCastigator));
        }
        return new ResponseEntity<>(jocFinalDtoDtos, HttpStatus.OK);
    }

    @PostMapping("/{idJoc}/raspuns")
    public ResponseEntity<?> seteazaRaspuns(@PathVariable Integer idJoc, @RequestBody ObjectNode objectNode) {

        String username = objectNode.get("username").asText();
        String raspuns = objectNode.get("raspuns").asText();
        Integer numarPuncte = Integer.parseInt(objectNode.get("numarPuncte").asText());

        Jucator jucator = Jucator.builder()
                .numarPuncte(numarPuncte)
                .username(username)
                .raspuns(raspuns)
                .build();

        Joc joc = mainService.trimiteRaspuns(jucator, idJoc);

        return new ResponseEntity<>(convertToJocDto(joc), HttpStatus.OK);

    }



    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .stareJoc(joc.getStareJoc())
                .cuvant1(joc.getCuvant1())
                .cuvant2(joc.getCuvant2())
                .cuvant3(joc.getCuvant3())
                .build();
    }

    private JocFinalDto convertToJocFinalDto(Joc joc, Jucator jucator) {
        return JocFinalDto.builder()
                .idJoc(joc.getId())
                .cuvant1(joc.getCuvant1())
                .cuvant2(joc.getCuvant2())
                .cuvant3(joc.getCuvant3())
                .castigator(jucator.getUsername())
                .puncte(jucator.getNumarPuncte())
                .build();
    }

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                .castigator(jucator.isCastigator())
                .raspuns(jucator.getRaspuns())
                .numarPuncte(jucator.getNumarPuncte())
                .username(jucator.getUsername())
                .build();
    }
}
