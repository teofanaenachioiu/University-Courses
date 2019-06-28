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
    private String mesaj;
    private Long data_intr;
    private String username;
    private String path_avatar;
}
