package domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Joc {
    private Integer id;
    private StareJoc stareJoc;
    private Integer nrMutari;
    private String traseu;
}
