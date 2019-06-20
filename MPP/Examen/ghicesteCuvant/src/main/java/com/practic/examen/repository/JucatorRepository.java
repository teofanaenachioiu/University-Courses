package com.practic.examen.repository;

import com.practic.examen.domain.Jucator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface JucatorRepository extends JpaRepository<Jucator, Integer> {
    @Transactional
    @Modifying
    @Query("update Jucator j set j.cuvantPropus = :cuvant where j.id=:id")
    void setCuvantPropus(@Param("id") Integer id, @Param("cuvant") String cuvant);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.muta = :muta where j.id=:id")
    void setMuta(@Param("id") Integer id, @Param("muta") boolean muta);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.castigator = :castigator where j.id=:id")
    void setCastigator(@Param("id") Integer id, @Param("castigator") boolean castigator);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.litereGhiciteDinCuvant = :litere where j.id=:id")
    void setLitereGhicite(@Param("id") Integer id, @Param("litere") String litere);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.numarPuncte = :numar where j.id=:id")
    void setNumarPuncte(@Param("id") Integer id, @Param("numar") Integer numar);

    @Transactional
    @Modifying
    @Query("update Jucator j set j.litereIncercate = :litere where j.id=:id")
    void setLitere(@Param("id") Integer id, @Param("litere") String litere);
}
