package com.api.nba.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamVsTeamRepository extends JpaRepository<TeamVsTeam, Long> {
    Optional<TeamVsTeam> findByTeam(String team);
}
