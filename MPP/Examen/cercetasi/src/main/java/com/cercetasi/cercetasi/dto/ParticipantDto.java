package com.cercetasi.cercetasi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ParticipantDto {
    private Integer id;
    private String nume;
    private Long oraSosirii;
    private Integer ultimulPunctDeControl;
}
