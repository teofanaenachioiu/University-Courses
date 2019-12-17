package com.exemple.app.repository;

import com.exemple.app.domain.Loc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocRepository extends JpaRepository<Loc, Integer> {
    @Query("select count(numar) from Vanzare v, Loc l where v.id = l.vanzareMapata.id and l.numar = :numar and v.spectacolMapat.id = :id")
    int isLocOcupat(@Param("numar") Integer nrLoc, @Param("id") Integer id);

    @Query("select l.numar from Vanzare v, Loc l where v.id = l.vanzareMapata.id and v.spectacolMapat.id = :id")
    List<Integer> getLocuriOcupateLaSpectacol(@Param("id") Integer id);
}
