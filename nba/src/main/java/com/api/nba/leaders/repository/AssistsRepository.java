package com.api.nba.leaders.repository;

import com.api.nba.leaders.model.Assists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssistsRepository extends JpaRepository<Assists, Long> {
    @Query(value = "SELECT * FROM assists_leaders_alltime ORDER BY assists DESC LIMIT :limit", nativeQuery = true)
    List<Assists> findTopPlayers(@Param("limit") Integer limit);
}
