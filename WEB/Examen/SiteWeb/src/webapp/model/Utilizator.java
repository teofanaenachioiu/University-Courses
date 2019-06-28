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
    private String username;
    private String password;
    private String path_avatar;
    private String descriere;
    private Long datan;
    private Double medie;
}
