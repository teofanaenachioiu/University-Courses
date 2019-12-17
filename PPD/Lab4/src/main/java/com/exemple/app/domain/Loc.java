package com.exemple.app.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Builder
@Entity
public class Loc extends HasID<Integer>{
    private int numar;
    private int pret;
    private Categorie categorie;

    @ManyToOne
    private Vanzare vanzareMapata;
}
