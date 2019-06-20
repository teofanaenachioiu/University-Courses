package com.cercetasi.cercetasi.repository;

import com.cercetasi.cercetasi.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
}
