package com.skijummping.skijummping.domain;

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
    private Integer pozitieAvion;
    private boolean muta;
    private boolean castigator;
    @ManyToOne
    private Joc jocMapat;
}
