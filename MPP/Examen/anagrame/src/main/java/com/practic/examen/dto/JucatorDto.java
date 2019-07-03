package com.practic.examen.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JucatorDto {
    private Integer id;
    private String username;
    private String raspuns;
    private boolean castigator;
    private Integer numarPuncte; //optional
}