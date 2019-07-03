package com.practic.examen.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Jucator extends BaseEntity<Integer> {
    private String username;
    private boolean muta;
    private boolean castigator;
    private Integer pozitie1;
    private Integer pozitie2;
    private Integer nrIncercari;
    private boolean pozitie1Ghicita;
    private boolean pozitie2Ghicita;
    @ManyToOne
    private Joc jocMapat;
}