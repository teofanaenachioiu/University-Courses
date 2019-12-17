package com.exemple.app.controller;

import com.exemple.app.service.MainService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class MainController {
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/locuri/{id}")
    public ResponseEntity<List<Integer>> getLocuriLibere(@PathVariable Integer id) {
        List<Integer> lista = mainService.getLocuriLibere(id);
        System.out.println("Trimit " + lista.size() + " locuri la client");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/spectacole")
    public ResponseEntity<List<Integer>> getIdSpectacole() {
        List<Integer> lista = mainService.getIdSpectacole();

        System.out.println("Trimit " + lista.size() + " id-uri de spectacole");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping(value = "/vanzare")
    public ResponseEntity<?> addVanzare(@RequestBody ObjectNode objectNode) throws ExecutionException, InterruptedException {

        int idSpectacol = objectNode.get("id").asInt();
        int numarBilete = objectNode.get("numar").asInt();

        List<Integer> locuri = new ArrayList<>();
        JsonNode jsonObj = objectNode.get("locuri");

        for (int i = 0; i < jsonObj.size(); i++) {
            int numarScaun = jsonObj.get(i).asInt();
            locuri.add(numarScaun);
        }

        boolean vandut = mainService.adaugaVanzare(idSpectacol, numarBilete, locuri);
        System.out.println("Vandut? " + vandut);
        if (vandut) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
