package com.api.nba.players.repository;

import com.api.nba.players.model.AllPlayers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllPlayersRepository extends JpaRepository<AllPlayers, Long> {
}
