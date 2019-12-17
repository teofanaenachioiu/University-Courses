package com.exemple.app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SpectacolDto {
    private int id;
    private long data;
    private String titlu;
    private String descriere;
}
