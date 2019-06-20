package com.example.talentshow.dto;
import com.example.talentshow.domain.StatusParticipant;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ParticipantDto {
    private Integer id;
    private String nume;
    private StatusParticipant statusParticipant;
    private Integer numarPuncteJurat;
}
