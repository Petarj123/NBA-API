package com.api.nba.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsPerSeasonRepository extends JpaRepository<StandingsPerSeason, Long> {
    List<StandingsPerSeason> findBySeason(String season);
}
