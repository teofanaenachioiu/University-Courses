package com.skijummping.skijummping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JocFinalDto {
    private Integer id;
    private String usernameJucator1;
    private String usernameJucator2;
    private Integer pozitieAvion1;
    private Integer pozitieAvion2;
    private String catigator;
}
