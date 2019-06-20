package com.skijummping.skijummping.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant extends BaseEntity<Integer> {
    private String nume;
    @Enumerated(EnumType.STRING)
    private StatusParticipant statusParticipant;
    private Integer numarPuncte;
    private Integer numarPunctePartial;
    private boolean arePunctePentruLungime;
    private boolean arePunctePentruStil;
    private boolean arePunctePentruAterizare;
}
