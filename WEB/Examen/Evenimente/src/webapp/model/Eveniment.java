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
public class Eveniment implements Serializable {
    private Integer id;
    private Long dataEv;
    private Long oraEv;
    private String descriere;
}
