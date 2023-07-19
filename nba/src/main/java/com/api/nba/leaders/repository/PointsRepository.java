package com.api.nba.leaders.repository;

import com.api.nba.leaders.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Points, Long> {
    @Query(value = "SELECT * FROM points_leaders_alltime ORDER BY points DESC LIMIT :limit", nativeQuery = true)
    List<Points> findTopPlayers(@Param("limit") Integer limit);
}
