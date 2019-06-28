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
public class DisponibilitateZbor implements Serializable {
    private Integer id;
    private Integer idZbor;
    private Integer zi;
}
