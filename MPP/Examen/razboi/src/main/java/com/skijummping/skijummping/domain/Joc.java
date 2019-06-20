package com.skijummping.skijummping.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(callSuper = true, exclude = {"jucatori"})
@EqualsAndHashCode(callSuper = true, exclude = {"jucatori"})

public class Joc extends BaseEntity<Integer>{
    @Enumerated(EnumType.STRING)
    private StareJoc stareJoc;
    private Integer mutariTura;
    @OneToMany(mappedBy = "jocMapat", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Jucator> jucatori;
}
