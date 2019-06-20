package com.practic.examen.dto;

import com.practic.examen.domain.StareJoc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JocFinalDto {
    private Integer idJoc;
    private StareJoc stareJoc;
    private String cuvant1;
    private String cuvant2;
    private String cuvant3;
    private String castigator;
    private Integer puncte;
}
