package com.exemple.app.repository;

import com.exemple.app.domain.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VanzareRepository extends JpaRepository<Vanzare, Integer> {
    @Query("select sum(l.pret) from Loc l, Vanzare v where l.vanzareMapata.id = v.id and v.spectacolMapat.id= :id")
    Integer getSoldSpectacol(@Param("id") Integer id);
}
