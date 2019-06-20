package com.cercetasi.cercetasi.controller;

import com.cercetasi.cercetasi.dto.ParticipantDto;
import com.cercetasi.cercetasi.service.CercetasiService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cercetasi")
@RestController
public class CercetasiController {
    private CercetasiService cercetasiService;

    @Autowired
    public CercetasiController(CercetasiService cercetasiService) {
        this.cercetasiService = cercetasiService;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<List<ParticipantDto>> getParticipatiControl(@PathVariable String username){
        return new ResponseEntity<>(cercetasiService.getParticipantiControl(username), HttpStatus.OK);
    }

    @PostMapping(value = "/passed")
    public ResponseEntity<?> passParticipant(@RequestBody ObjectNode objectNode){
        Integer idControlParticipant = Integer.parseInt(objectNode.get("id").asText());
        Long oraSosirii = Long.parseLong(objectNode.get("oraSosirii").asText());
        this.cercetasiService.participantPassed(idControlParticipant, oraSosirii);
        return new ResponseEntity<>("passed", HttpStatus.OK);
    }
}
