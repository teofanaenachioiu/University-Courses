package com.skijummping.skijummping.repository;

import com.skijummping.skijummping.domain.Joc;
import com.skijummping.skijummping.domain.StareJoc;
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
}
