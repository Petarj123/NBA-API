package com.api.nba.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    @Query("SELECT c.team FROM Conference c WHERE c.conference = 'Eastern'")
    List<String> findEasternConferenceTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.conference = 'Western'")
    List<String> findWesternConferenceTeamNames();

    @Query("SELECT c.team FROM Conference c WHERE c.division = 'Atlantic'")
    List<String> findAtlanticDivisionTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.division = 'Central'")
    List<String> findCentralDivisionTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.division = 'Southeast'")
    List<String> findSouthEastDivisionTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.division = 'SouthWest'")
    List<String> findSouthWestDivisionTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.division = 'NorthWest'")
    List<String> findNorthWestDivisionTeamNames();
    @Query("SELECT c.team FROM Conference c WHERE c.division = 'Pacific'")
    List<String> findPacificDivisionTeamNames();
}
