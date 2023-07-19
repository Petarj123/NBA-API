package com.api.nba.leaders;

import com.api.nba.DTO.AssistsLeader;
import com.api.nba.DTO.PointsLeader;
import com.api.nba.DTO.ReboundsLeader;
import com.api.nba.DTO.StealsLeader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeadersService {

    private final AssistsRepository assistsRepository;
    private final PointsRepository pointsRepository;
    private final ReboundsRepository reboundsRepository;
    private final StealsRepository stealsRepository;

    public List<PointsLeader> scoringLeadersList(Integer numberOfPLayers){
        if (numberOfPLayers == null){
            List<Points> players = pointsRepository.findAll();
            return players.stream().map(player -> new PointsLeader(player.getRank(), player.getPlayerName(), player.getPoints())).toList();
        }
        List<Points> players = pointsRepository.findTopPlayers(numberOfPLayers);
        return players.stream().map(player -> new PointsLeader(player.getRank(), player.getPlayerName(), player.getPoints())).toList();
    }
    public List<AssistsLeader> assistsLeaderList(Integer numberOfPLayers){
        if (numberOfPLayers == null){
            List<Assists> players = assistsRepository.findAll();
            return players.stream().map(player -> new AssistsLeader(player.getRank(), player.getPlayerName(), player.getAssists())).toList();
        }
        List<Assists> players = assistsRepository.findTopPlayers(numberOfPLayers);
        return players.stream().map(player -> new AssistsLeader(player.getRank(), player.getPlayerName(), player.getAssists())).toList();
    }
    public List<ReboundsLeader> reboundsLeaderList(Integer numberOfPlayers){
        if (numberOfPlayers == null){
            List<Rebounds> players = reboundsRepository.findAll();
            return players.stream().map(player -> new ReboundsLeader(player.getRank(), player.getPlayerName(), player.getRebounds())).toList();
        }
        List<Rebounds> players = reboundsRepository.findTopPlayers(numberOfPlayers);
        return players.stream().map(player -> new ReboundsLeader(player.getRank(), player.getPlayerName(), player.getRebounds())).toList();
    }
    public List<StealsLeader> stealsLeaderList(Integer numberOfPlayers){
        if (numberOfPlayers == null){
            List<Steals> players = stealsRepository.findAll();
            return players.stream().map(player -> new StealsLeader(player.getRank(), player.getPlayerName(), player.getSteals())).toList();
        }
        List<Steals> players = stealsRepository.findTopPlayers(numberOfPlayers);
        return players.stream().map(player -> new StealsLeader(player.getRank(), player.getPlayerName(), player.getSteals())).toList();
    }
}
