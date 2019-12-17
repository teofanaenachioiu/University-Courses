package com.exemple.app.domain;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
@Builder
@Entity
public class Spectacol extends HasID<Integer>{
    private long data;
    private String titlu;
    private String descriere;

    @OneToMany(mappedBy = "spectacolMapat", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Vanzare> listaVanzari;
}
