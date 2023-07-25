package com.api.nba.players.service;

import com.api.nba.DTO.PlayerData;
import com.api.nba.DTO.PlayerStat;
import com.api.nba.exceptions.PlayerNotFoundException;
import com.api.nba.players.model.AllPlayers;
import com.api.nba.players.model.Player;
import com.api.nba.players.model.PlayoffPlayer;
import com.api.nba.players.repository.PlayerRepository;
import com.api.nba.players.repository.PlayoffPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayoffPlayerRepository playoffPlayerRepository;

    public List<PlayerData> retrievePlayerRegularSeasons(String firstName, String lastName) throws PlayerNotFoundException {
        List<Player> players = playerRepository.findByFirstNameAndLastName(firstName, lastName);

        if (players.isEmpty()) {
            throw new PlayerNotFoundException("No player found with the given first and last name.");
        }

        Map<AllPlayers, List<Player>> playersGrouped = players.stream()
                .collect(Collectors.groupingBy(Player::getAllPlayers));

        List<PlayerData> playerDataList = new ArrayList<>();
        for (Map.Entry<AllPlayers, List<Player>> entry : playersGrouped.entrySet()) {
            AllPlayers allPlayer = entry.getKey();
            List<Player> playersWithName = entry.getValue();

            String name = allPlayer.getFirstName() + " " + allPlayer.getLastName();

            List<PlayerStat> stats = playersWithName.stream().map(player -> new PlayerStat(player.getSeason(), player.getLeague(), player.getAge(),
                    player.getTeam(), player.getPosition(), player.getGamesPlayed(),
                    player.getGamesStarted(), player.getMinutesPerGame(), player.getFieldGoals(),
                    player.getFieldGoalAttempts(), player.getFieldGoalPercentage(), player.getThreePointFieldGoals(),
                    player.getThreePointFieldGoalAttempts(), player.getThreePointFieldGoalPercentage(), player.getTwoPointFieldGoals(),
                    player.getTwoPointFieldGoalAttempts(), player.getTwoPointFieldGoalPercentage(), player.getEffectiveFieldGoalPercentage(),
                    player.getFreeThrows(), player.getFreeThrowAttempts(), player.getFreeThrowPercentage(), player.getOffensiveRebounds(),
                    player.getDefensiveRebounds(), player.getTotalRebounds(), player.getAssists(), player.getSteals(), player.getBlocks(),
                    player.getTurnovers(), player.getPersonalFouls(), player.getPoints())
            ).collect(Collectors.toList());

            playerDataList.add(new PlayerData(name, stats));
        }

        return playerDataList;
    }
    public List<PlayerData> retrievePlayerPlayoffSeasons(String firstName, String lastName) throws PlayerNotFoundException {
        List<PlayoffPlayer> players = playoffPlayerRepository.findByFirstNameAndLastName(firstName, lastName);

        if (players.isEmpty()) {
            throw new PlayerNotFoundException("No player found with the given first and last name.");
        }

        Map<AllPlayers, List<PlayoffPlayer>> playersGrouped = players.stream()
                .collect(Collectors.groupingBy(PlayoffPlayer::getAllPlayers));

        List<PlayerData> playerDataList = new ArrayList<>();
        for (Map.Entry<AllPlayers, List<PlayoffPlayer>> entry : playersGrouped.entrySet()) {
            AllPlayers allPlayer = entry.getKey();
            List<PlayoffPlayer> playersWithName = entry.getValue();

            String name = allPlayer.getFirstName() + " " + allPlayer.getLastName();

            List<PlayerStat> stats = playersWithName.stream().map(player -> new PlayerStat(player.getSeason(), player.getLeague(), player.getAge(),
                    player.getTeam(), player.getPosition(), player.getGamesPlayed(),
                    player.getGamesStarted(), player.getMinutesPerGame(), player.getFieldGoals(),
                    player.getFieldGoalAttempts(), player.getFieldGoalPercentage(), player.getThreePointFieldGoals(),
                    player.getThreePointFieldGoalAttempts(), player.getThreePointFieldGoalPercentage(), player.getTwoPointFieldGoals(),
                    player.getTwoPointFieldGoalAttempts(), player.getTwoPointFieldGoalPercentage(), player.getEffectiveFieldGoalPercentage(),
                    player.getFreeThrows(), player.getFreeThrowAttempts(), player.getFreeThrowPercentage(), player.getOffensiveRebounds(),
                    player.getDefensiveRebounds(), player.getTotalRebounds(), player.getAssists(), player.getSteals(), player.getBlocks(),
                    player.getTurnovers(), player.getPersonalFouls(), player.getPoints())
            ).collect(Collectors.toList());

            playerDataList.add(new PlayerData(name, stats));
        }

        return playerDataList;
    }



}
