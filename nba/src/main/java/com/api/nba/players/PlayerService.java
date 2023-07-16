package com.api.nba.players;

import com.api.nba.DTO.PlayerData;
import com.api.nba.DTO.PlayerStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerData retrievePlayerSeasons(String firstName, String lastName){
        List<Player> players = playerRepository.findByFirstNameAndLastName(firstName, lastName);
        List<PlayerStat> stats = players.stream().map(player -> new PlayerStat(player.getSeason(), player.getLeague(), player.getAge(),
                player.getTeam(), player.getPosition(), player.getGamesPlayed(),
                player.getGamesStarted(), player.getMinutesPerGame(), player.getFieldGoals(),
                player.getFieldGoalAttempts(), player.getFieldGoalPercentage(), player.getThreePointFieldGoals(),
                player.getThreePointFieldGoalAttempts(), player.getThreePointFieldGoalPercentage(), player.getTwoPointFieldGoals(),
                player.getTwoPointFieldGoalAttempts(), player.getTwoPointFieldGoalPercentage(), player.getEffectiveFieldGoalPercentage(),
                player.getFreeThrows(), player.getFreeThrowAttempts(), player.getFreeThrowPercentage(), player.getOffensiveRebounds(),
                player.getDefensiveRebounds(), player.getTotalRebounds(), player.getAssists(), player.getSteals(), player.getBlocks(),
                player.getTurnovers(), player.getPersonalFouls(), player.getPoints())
        ).toList();
        return new PlayerData(firstName + " " + lastName, stats);
    }

}
