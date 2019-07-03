package com.practic.examen.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Utilizator {
    @Id
    private String username;
    private String password;
    private boolean conectat;

}
