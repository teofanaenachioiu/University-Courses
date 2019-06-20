package com.practic.examen.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practic.examen.domain.Joc;
import com.practic.examen.domain.Jucator;
import com.practic.examen.dto.JocDto;
import com.practic.examen.dto.JucatorDto;
import com.practic.examen.dto.LitereDto;
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

    @PostMapping(value = "raspunde/{idAdversar}")
    public ResponseEntity<?> adaugaCuvantJucator(@PathVariable Integer idAdversar, @RequestBody ObjectNode objectNode) {
        Integer idJucator = Integer.parseInt(objectNode.get("id").asText());
        String literaPropusa = objectNode.get("cuvantPropus").asText();
        mainService.verificaLitera(idAdversar, idJucator, literaPropusa);
        return new ResponseEntity<>("s-a verificat litera. s-a schimbat tura", HttpStatus.OK);
    }

    @GetMapping(value = "details/{username}/{idJoc}")
    public ResponseEntity<?> getDetalii(@PathVariable String username, @PathVariable Integer idJoc) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        LitereDto litereDto = LitereDto.builder()
                .litere(jucator.getLitereIncercate())
                .numarPuncte(jucator.getNumarPuncte())
                .build();
        return new ResponseEntity<>(litereDto, HttpStatus.OK);
    }

    @PostMapping(value = "/adaugaCuvant")
    public ResponseEntity<?> adaugaCuvantJucator(@RequestBody ObjectNode objectNode) {
        Integer idJucator = Integer.parseInt(objectNode.get("id").asText());
        String cuvantPropus = objectNode.get("cuvantPropus").asText();
        mainService.setCuvantJucator(idJucator, cuvantPropus);
        return new ResponseEntity<>("cuvant adaugat", HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/schimbaTura")
    public ResponseEntity<?> schimbaTura(@PathVariable Integer idJoc) {
        Integer idJucator = mainService.schimbaTura(idJoc);
        return new ResponseEntity<>("este tura lui " + idJucator, HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getJucator/{username}")
    public ResponseEntity<?> schimbaTura(@PathVariable Integer idJoc, @PathVariable String username) {
        Jucator jucator = mainService.getJucator(idJoc, username);
        return new ResponseEntity<>(convertToJucatorDto(jucator), HttpStatus.OK);
    }

    @GetMapping(value = "/{idJoc}/getAdversari/{username}")
    public ResponseEntity<?> getJucatori(@PathVariable Integer idJoc, @PathVariable String username) {
        List<Jucator> listaJucatori = mainService.getJucatori(idJoc, username);
        List<JucatorDto> listaJucatoriDto = new ArrayList<>();
        for (Jucator jucator : listaJucatori) {
            listaJucatoriDto.add(convertToJucatorDtoAdversar(jucator));
        }
        return new ResponseEntity<>(listaJucatoriDto, HttpStatus.OK);
    }

    private JocDto convertToJocDto(Joc joc) {
        return JocDto.builder()
                .id(joc.getId())
                .stareJoc(joc.getStareJoc())
                .numarLitereGhicite(joc.getNumarLitereGhicite())
                .build();
    }

    private JucatorDto convertToJucatorDtoAdversar(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                .username(jucator.getUsername())
                .numarPuncte(jucator.getNumarPuncte())
                .muta(jucator.isMuta())
                .castigator(jucator.isCastigator())
                .cuvantPropus(modifica(jucator.getCuvantPropus(), jucator.getLitereGhiciteDinCuvant()))
                .build();
    }

    private String modifica(String cuvant, String litereghicite) {
        String cuvantNou = "";
        if (cuvant == null || cuvant.equals("")) return "";
        for (int i = 0; i < cuvant.length(); i++) {
            String litera = String.valueOf(cuvant.charAt(i));
            if ("AEOIUaeiou".contains(litera) && !litereghicite.contains(litera)) {
                cuvantNou = cuvantNou + "V";
            } else if (!"AEOIUaeiou".contains(litera) && !litereghicite.contains(litera)) {
                cuvantNou = cuvantNou + "C";
            } else cuvantNou = cuvantNou + litera;
        }
        return cuvantNou;
    }

    private JucatorDto convertToJucatorDto(Jucator jucator) {
        return JucatorDto.builder()
                .id(jucator.getId())
                .username(jucator.getUsername())
                .numarPuncte(jucator.getNumarPuncte())
                .muta(jucator.isMuta())
                .castigator(jucator.isCastigator())
                .cuvantPropus(jucator.getCuvantPropus())
                .build();
    }
}
