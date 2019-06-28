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
    private Integer idIntrebare;
    private Integer idUtilizator;
    private Integer rasp;

}
