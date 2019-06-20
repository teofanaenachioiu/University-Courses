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
    private Integer numarPuncte; //optional
    @ManyToOne
    private Joc jocMapat;
}