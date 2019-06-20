package com.skijummping.skijummping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JucatorDto {
    private Integer id;
    private String username;
    private Integer pozitieAvion;
    private boolean muta;
    private boolean castigator;
}
