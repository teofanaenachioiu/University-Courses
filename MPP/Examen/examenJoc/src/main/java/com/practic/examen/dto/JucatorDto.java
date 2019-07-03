package com.practic.examen.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JucatorDto {
    private Integer id;
    private String username;
    private boolean muta;
    private boolean castigator;
    private Integer pozitie1;
    private Integer pozitie2;
    private Integer nrIncercari;
    private boolean pozitie1Ghicita;
    private boolean pozitie2Ghicita;
}