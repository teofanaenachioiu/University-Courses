package com.practic.examen.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Cuvant extends BaseEntity<Integer>{
    private String denumire;
}
