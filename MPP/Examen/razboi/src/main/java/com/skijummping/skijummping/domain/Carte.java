package com.skijummping.skijummping.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Carte extends BaseEntity<Integer> {
    private String denumire;
    private boolean activa;
    private boolean castigata;
    @ManyToOne
    private Jucator jucatorMapat;
}
