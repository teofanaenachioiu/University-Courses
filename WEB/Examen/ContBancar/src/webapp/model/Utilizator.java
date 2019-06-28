package webapp.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilizator implements Serializable {
    private String username;
    private String password;
    private Integer cont;
    private String nume;
    private Integer suma;
}
