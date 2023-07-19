package com.api.nba.teams.repository;

import com.api.nba.teams.model.TeamVsTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamVsTeamRepository extends JpaRepository<TeamVsTeam, Long> {
    List<TeamVsTeam>findByTeam(String team);
}
