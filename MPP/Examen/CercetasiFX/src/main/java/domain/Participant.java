package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant implements Serializable {
    private Integer id;
    private String nume;
    private Long oraSosirii;
    private Integer ultimulPunctDeControl;
}
