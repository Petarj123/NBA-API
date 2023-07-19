package com.api.nba.leaders.repository;

import com.api.nba.leaders.model.Steals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StealsRepository extends JpaRepository<Steals, Long> {
    @Query(value = "SELECT * FROM steals_leaders_alltime ORDER BY steals DESC LIMIT :limit", nativeQuery = true)
    List<Steals> findTopPlayers(@Param("limit") Integer limit);
}
