package webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persoana implements Serializable {
    private Integer id;
    private String nume;
    private String prenume;
    private String email;
    private Integer idZbor;
}
