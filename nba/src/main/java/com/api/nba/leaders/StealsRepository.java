package com.api.nba.leaders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StealsRepository extends JpaRepository<Steals, Long> {
    List<Steals> findAllByOrderByPlayerNameAsc();
}
