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
    private Integer pozitie1;
    private Integer pozitie2;
    private Integer nrIncercari;
    private boolean pozitie1Ghicita;
    private boolean pozitie2Ghicita;
}
