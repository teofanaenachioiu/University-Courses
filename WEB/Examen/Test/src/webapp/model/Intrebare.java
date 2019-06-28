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
public class Intrebare implements Serializable {
    private Integer id;
    private String intr;
    private String r1;
    private String r2;
    private String r3;
    private Integer rc;
}
