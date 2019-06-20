package com.example.talentshow.controller;

import com.example.talentshow.domain.Utilizator;
import com.example.talentshow.service.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ObjectNode objectNode)  {
        try {
            String username = objectNode.get("username").asText();
            String password = objectNode.get("password").asText();

            Utilizator utilizator = authService.login(Utilizator.builder()
                    .username(username)
                    .password(password)
                    .build());

            return new ResponseEntity<>(utilizator, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody ObjectNode objectNode) {

        String username = objectNode.get("username").asText();
        try{
            authService.logout(Utilizator.builder().username(username).build());
            return new ResponseEntity<>( "Logout user", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
