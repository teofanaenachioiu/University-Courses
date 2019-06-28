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
public class Raspuns implements Serializable {
    private Integer id;
    private String mesaj;
    private Long data_rasp;
    private Integer idIntrebare;
    private String username;
    private String path_avatar;
}