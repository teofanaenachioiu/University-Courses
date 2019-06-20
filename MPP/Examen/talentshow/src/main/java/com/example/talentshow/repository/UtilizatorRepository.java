package com.example.talentshow.repository;

import com.example.talentshow.domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, String> {
}
