package domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Jucator implements Serializable {
    private Integer id;
    private String username;
    private boolean muta;
    private boolean castigator;
    private Integer numarPuncte; //optional
    private String cuvantPropus;
}
