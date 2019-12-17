package client;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class VanzareClient {
    private int id;
    private int numar;
    private List<Integer> locuri;
}
