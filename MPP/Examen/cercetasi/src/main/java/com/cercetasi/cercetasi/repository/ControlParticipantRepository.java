package com.cercetasi.cercetasi.repository;

import com.cercetasi.cercetasi.domain.ControlParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ControlParticipantRepository extends JpaRepository<ControlParticipant, Integer> {
}
