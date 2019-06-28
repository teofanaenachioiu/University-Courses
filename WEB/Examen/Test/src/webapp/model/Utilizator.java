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
public class Utilizator implements Serializable {
    private Integer id;
    private String nume;
    private String email;
    private Integer punctaj;
}
