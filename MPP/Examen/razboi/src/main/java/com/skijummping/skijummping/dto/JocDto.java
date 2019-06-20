package com.skijummping.skijummping.dto;

import com.skijummping.skijummping.domain.StareJoc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JocDto implements Serializable {
    private Integer id;
    private StareJoc stareJoc;
    private Integer mutariTura;
}
