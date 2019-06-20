package com.practic.examen.dto;

import com.practic.examen.domain.StareJoc;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JocDto {
    private Integer id;
    private StareJoc stareJoc;
    private String cuvant1;
    private String cuvant2;
    private String cuvant3;
}