package com.exemple.app.repository;

import com.exemple.app.domain.Spectacol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpectacolRepository extends JpaRepository<Spectacol, Integer> {
}
