package com.cercetasi.cercetasi.controller;

import com.cercetasi.cercetasi.domain.Coordonator;
import com.cercetasi.cercetasi.dto.CoordonatorDto;
import com.cercetasi.cercetasi.service.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginSpectator(@RequestBody ObjectNode objectNode)  {
        try {
            String username = objectNode.get("username").asText();
            String password = objectNode.get("password").asText();

            CoordonatorDto coordonator = authService.loginCoordonator(Coordonator.builder()
                    .username(username)
                    .password(password)
                    .build());

            return new ResponseEntity<>(coordonator, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logoutSpectator(@RequestBody ObjectNode objectNode) {

        String username = objectNode.get("username").asText();
        try{
            authService.logoutCoordonator(Coordonator.builder().username(username).build());
            return new ResponseEntity<>( "Logout coordonator", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
