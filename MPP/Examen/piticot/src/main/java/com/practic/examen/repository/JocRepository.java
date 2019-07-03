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
    @Query("update Joc j set j.traseu = :traseu where j.id=:id")
    void setTraseu(@Param("id") Integer id, @Param("traseu") String traseu);

    @Transactional
    @Modifying
    @Query("update Joc j set j.nrMutari =:nrMutari where j.id=:id")
    void setNumarMutari(@Param("id") Integer id, @Param("nrMutari") Integer nrMutari);
}
