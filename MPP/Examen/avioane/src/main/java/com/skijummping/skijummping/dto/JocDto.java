package com.skijummping.skijummping.dto;

import com.skijummping.skijummping.domain.StareJoc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JocDto {
    private Integer id;
    private StareJoc stareJoc;
}
