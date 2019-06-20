package com.cercetasi.cercetasi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class CoordonatorDto {
    private String username;
    private String password;
    private Integer punctControl;
}
