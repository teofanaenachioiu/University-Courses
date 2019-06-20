package com.skijummping.skijummping.repository;

import com.skijummping.skijummping.domain.Participant;
import com.skijummping.skijummping.domain.StatusParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    @Query("select s from Participant s where s.statusParticipant = :status")
    List<Participant> getParticipantByStatus(@Param("status") StatusParticipant statusParticipant);
}
