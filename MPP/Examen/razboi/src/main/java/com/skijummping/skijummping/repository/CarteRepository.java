package com.skijummping.skijummping.repository;

import com.skijummping.skijummping.domain.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CarteRepository extends JpaRepository<Carte, Integer> {
    @Transactional
    @Modifying
    @Query("update Carte c set c.activa = :act where c.id=:id")
    void updateCarteActiva(@Param("id") Integer id, @Param("act") boolean act);
}
