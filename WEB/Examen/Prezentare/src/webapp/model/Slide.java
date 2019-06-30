package webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Slide implements Serializable {
    private Integer id;
    private String titlu;
    private String text;
    private String path_img;
    private String layout;
    private Integer nrSecunde;
    private Integer nrOrd;
}
