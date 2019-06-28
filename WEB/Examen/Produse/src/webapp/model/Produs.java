package webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produs implements Serializable {
    private Integer id;
    private String nume;
    private String descriere;
    private String producator;
    private Integer pret;
    private Integer cantitate;
    private String path_poza;
}
