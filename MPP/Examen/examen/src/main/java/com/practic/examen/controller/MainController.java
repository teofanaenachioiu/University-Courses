package com.practic.examen.controller;

import com.practic.examen.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
