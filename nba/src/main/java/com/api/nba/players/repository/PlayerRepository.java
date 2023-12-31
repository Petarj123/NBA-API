package com.api.nba.players.repository;

import com.api.nba.players.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByFirstNameAndLastName(String firstName, String lastName);

}
