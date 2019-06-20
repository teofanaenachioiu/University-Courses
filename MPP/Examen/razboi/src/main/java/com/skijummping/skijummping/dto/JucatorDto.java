package com.skijummping.skijummping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JucatorDto implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private boolean castigator;
}
