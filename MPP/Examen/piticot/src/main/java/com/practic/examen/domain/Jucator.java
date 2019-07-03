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
    private String pozitii;
    private String numereGenerate;
    private String litera;
    @ManyToOne
    private Joc jocMapat;
}