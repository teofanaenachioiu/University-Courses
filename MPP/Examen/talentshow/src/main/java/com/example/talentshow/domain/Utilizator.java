package com.example.talentshow.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private NrJurat nrJurat;
}
