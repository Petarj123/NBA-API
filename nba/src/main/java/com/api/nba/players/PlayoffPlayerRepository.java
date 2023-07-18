package com.api.nba.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayoffPlayerRepository extends JpaRepository<PlayoffPlayer, Long> {
}
