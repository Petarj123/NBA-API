package com.api.nba.teams.repository;

import com.api.nba.teams.model.StandingsPerSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsPerSeasonRepository extends JpaRepository<StandingsPerSeason, Long> {
    List<StandingsPerSeason> findBySeason(String season);
    List<StandingsPerSeason> findByTeam(String team);
    List<StandingsPerSeason> findBySeasonAndTeam(String season, String team);

}
