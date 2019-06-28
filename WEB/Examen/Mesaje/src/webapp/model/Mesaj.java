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
public class Mesaj implements Serializable {
    private int id;
    private String userExp;
    private String userDest;
    private String subiect;
    private String continut;
    private long dataExp;
    private boolean citit;
}
