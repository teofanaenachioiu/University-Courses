package com.example.talentshow.domain;

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
    private Integer numarPuncteJurat1;
    private Integer numarPuncteJurat2;
    private Integer numarPuncteJurat3;
}
