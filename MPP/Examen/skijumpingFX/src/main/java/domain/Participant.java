package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant implements Serializable {
    private Integer id;
    private String nume;
    private StatusParticipant statusParticipant;
    private Integer numarPuncte;
    private Integer numarPunctePartial;
    private boolean arePunctePentruLungime;
    private boolean arePunctePentruStil;
    private boolean arePunctePentruAterizare;
}
