package com.exemple.app.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Builder

public class Vanzare extends HasID<Integer> {
    private long data;
    private int numarBilete;

    @OneToMany(mappedBy = "vanzareMapata", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Loc> listaLocuri;

    @ManyToOne
    private Spectacol spectacolMapat;
}
