package com.practic.examen.domain;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, exclude = {"jucatori"})
@EqualsAndHashCode(callSuper = true, exclude = {"jucatori"})
@Builder
public class Joc extends BaseEntity<Integer>{
    @Enumerated(EnumType.STRING)
    private StareJoc stareJoc;

    private String cuvant1;
    private String cuvant2;
    private String cuvant3;

    private Integer numarMutari;

    @OneToMany(mappedBy = "jocMapat", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Jucator> jucatori;

}