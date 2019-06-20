package com.practic.examen.repository;

import com.practic.examen.domain.Jucator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface JucatorRepository extends JpaRepository<Jucator, Integer> {
    @Transactional
    @Modifying
    @Query("update Jucator j set j.numarPuncte = :numar where j.id=:id")
    void setNumarPuncte(@Param("id") Integer id, @Param("numar") Integer numar);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.castigator = :castigator where j.id=:id")
    void setCastigator(@Param("id") Integer id, @Param("castigator") boolean castigator);
}
