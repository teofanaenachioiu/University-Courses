package com.practic.examen.repository;

import com.practic.examen.domain.Cuvant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuvantRepository extends JpaRepository<Cuvant, Integer> {
}
