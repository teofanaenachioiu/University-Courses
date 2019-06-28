package webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Popularitate {
    private Integer nrViews;
    private Integer nrLuna;
    private Integer idProdus;
}
