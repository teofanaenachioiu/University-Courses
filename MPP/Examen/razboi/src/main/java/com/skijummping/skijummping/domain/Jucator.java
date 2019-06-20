package com.skijummping.skijummping.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, exclude = {"carti"})
@EqualsAndHashCode(callSuper = true, exclude = {"carti"})
@Builder
public class Jucator extends BaseEntity<Integer> {
    private String username;
    private String password;
    private boolean castigator;
    @ManyToOne
    private Joc jocMapat;
    @OneToMany(mappedBy = "jucatorMapat", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Carte> carti;
}
