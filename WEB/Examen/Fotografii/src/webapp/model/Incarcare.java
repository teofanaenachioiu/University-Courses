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
public class Incarcare implements Serializable {
    private Integer id;
    private String username;
    private String path_poza;
    private String descriere;
}
