package com.practic.examen.repository;

import com.practic.examen.domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, String> {
    @Transactional
    @Modifying
    @Query("update Utilizator u set u.conectat = :conn where u.username=:username")
    void setConectat(@Param("username") String username, @Param("conn") boolean conn);
}
