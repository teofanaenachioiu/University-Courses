package com.cercetasi.cercetasi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"controlParticipanti"})
@ToString(callSuper = true, exclude = {"controlParticipanti"})
@Builder
public class Participant extends BaseEntity<Integer> {
    private String nume;
    @OneToMany(mappedBy = "participantMapat", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ControlParticipant> controlParticipanti;
}
