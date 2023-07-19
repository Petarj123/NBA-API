package com.api.nba.leaders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReboundsRepository extends JpaRepository<Rebounds, Long> {
    @Query(value = "SELECT * FROM rebounds_leaders_alltime ORDER BY rebounds DESC LIMIT :limit", nativeQuery = true)
    List<Rebounds> findTopPlayers(@Param("limit") Integer limit);
}
