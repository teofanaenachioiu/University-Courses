package com.practic.examen.repository;

import com.practic.examen.domain.Joc;
import com.practic.examen.domain.StareJoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface JocRepository extends JpaRepository<Joc, Integer> {
    @Transactional
    @Modifying
    @Query("update Joc j set j.stareJoc = :stare where j.id=:id")
    void setStareJoc(@Param("id") Integer id, @Param("stare") StareJoc stare);

    @Transactional
    @Modifying
    @Query("update Joc j set j.numarLitereGhicite = :numar where j.id=:id")
    void setNumarLitereGhicite(@Param("id") Integer id, @Param("numar") Integer numar);
}
