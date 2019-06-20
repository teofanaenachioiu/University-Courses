package com.cercetasi.cercetasi.domain;

import lombok.AllArgsConstructor;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"controlParticipanti"})
@ToString(exclude = {"controlParticipanti"})
@Builder
public class Coordonator {
    @Id
    private String username;
    private String password;
    private Integer punctControl;

    @OneToMany(mappedBy = "coordonatorMapat", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ControlParticipant> controlParticipanti;
}
