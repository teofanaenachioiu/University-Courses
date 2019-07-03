package com.practic.examen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JucatorFinalDto {
    private Integer idJoc;
    private Integer idJucator;
    private Integer idAdversar;
    private String usernameJucator;
    private String usernameAdversar;
    private Integer pozitie1Jucator;
    private Integer pozitie2Jucator;
    private Integer pozitie1Adversar;
    private Integer pozitie2Adversar;
    private Integer numarIncercariJucator;
    private Integer numarIncercariAdversar;
    private String castigator;
}
