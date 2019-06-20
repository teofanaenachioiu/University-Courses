package com.skijummping.skijummping.repository;

import com.skijummping.skijummping.domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, String> {
    @Transactional
    @Modifying
    @Query("update Utilizator u set u.conectat = :conn where u.username=:username")
    void updateUtilizatorConnection(@Param("username") String username, @Param("conn") boolean conn);
}
