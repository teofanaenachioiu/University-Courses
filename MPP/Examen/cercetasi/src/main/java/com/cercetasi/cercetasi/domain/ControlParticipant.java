package com.cercetasi.cercetasi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ControlParticipant extends BaseEntity<Integer>{
    private Long oraSosire;

    @ManyToOne
    private Coordonator coordonatorMapat;
    @ManyToOne
    private Participant participantMapat;
}
