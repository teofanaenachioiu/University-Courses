package webapp.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tranzactie implements Serializable {
    private Integer id;
    private Integer contExp;
    private Integer contDest;
    private Integer suma;
    private Long dataora;
}
