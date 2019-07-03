package domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Jucator implements Serializable {
    private Integer id;
    private String username;
    private boolean muta;
    private String pozitii;
    private String numereGenerate;
    private String litera;
}
