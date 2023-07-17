package com.api.nba.teams;

import com.api.nba.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final ConferenceRepository conferenceRepository;
    private final StandingsPerSeasonRepository standingsPerSeasonRepository;
    private final TeamVsTeamRepository teamVsTeamRepository;




    public Teams getAllTeams() {
        List<Conference> teams = conferenceRepository.findAll();
        return new Teams(teams);
    }
    public ConferenceTeams getConferenceTeams(String conference){
        if (conference.equalsIgnoreCase("west")){
            return new ConferenceTeams("West", conferenceRepository.findWesternConferenceTeamNames());
        }
        else if (conference.equalsIgnoreCase("east")){
            return new ConferenceTeams("East", conferenceRepository.findEasternConferenceTeamNames());
        }
        throw new IllegalArgumentException("Conference can only be east or west");
    }
    public DivisionTeams getDivisionTeams(String division) {
        List<String> teams = switch (division.toLowerCase()) {
            case "atlantic" -> conferenceRepository.findAtlanticDivisionTeamNames();
            case "central" -> conferenceRepository.findCentralDivisionTeamNames();
            case "southeast" -> conferenceRepository.findSouthEastDivisionTeamNames();
            case "southwest" -> conferenceRepository.findSouthWestDivisionTeamNames();
            case "northwest" -> conferenceRepository.findNorthWestDivisionTeamNames();
            case "pacific" -> conferenceRepository.findPacificDivisionTeamNames();
            default -> throw new IllegalArgumentException("Invalid division");
        };
        return new DivisionTeams(division, teams);
    }
    public StandingsSimple getStandings(String season){
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);
        List<Ranking> resultList = list.stream().map(team -> new Ranking(team.getSeed(), team.getTeam(), team.getOverall())).toList();
        return new StandingsSimple(season, resultList);
    }
    public DivisionRankings getConferenceStandings(String season){
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);

        Map<String, Supplier<List<String>>> conferenceMap = new HashMap<>();
        conferenceMap.put("Western Conference", conferenceRepository::findWesternConferenceTeamNames);
        conferenceMap.put("Eastern Conference", conferenceRepository::findEasternConferenceTeamNames);

        List<Map<String, List<Ranking>>> finalResult = new ArrayList<>();

        for (Map.Entry<String, Supplier<List<String>>> entry : conferenceMap.entrySet()) {
            finalResult.add(Collections.singletonMap(entry.getKey(), processStandings(list, entry.getValue().get())));
        }

        return new DivisionRankings(season, finalResult);
    }

    public DivisionRankings getDivisionStandings(String season) {
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);

        Map<String, Supplier<List<String>>> divisionMap = new HashMap<>();
        divisionMap.put("Atlantic", conferenceRepository::findAtlanticDivisionTeamNames);
        divisionMap.put("Central", conferenceRepository::findCentralDivisionTeamNames);
        divisionMap.put("Pacific", conferenceRepository::findPacificDivisionTeamNames);
        divisionMap.put("SouthWest", conferenceRepository::findSouthWestDivisionTeamNames);
        divisionMap.put("NorthWest", conferenceRepository::findNorthWestDivisionTeamNames);
        divisionMap.put("SouthEast", conferenceRepository::findSouthEastDivisionTeamNames);

        List<Map<String, List<Ranking>>> finalResult = new ArrayList<>();

        for (Map.Entry<String, Supplier<List<String>>> entry : divisionMap.entrySet()) {
            finalResult.add(Collections.singletonMap(entry.getKey(), processStandings(list, entry.getValue().get())));
        }

        return new DivisionRankings(season, finalResult);
    }
    private List<Ranking> processStandings(List<StandingsPerSeason> list, List<String> divisionTeams) {
        return list.stream()
                .filter(standings -> divisionTeams.contains(standings.getTeam()))
                .sorted((s1, s2) -> {
                    int wins1 = Integer.parseInt(s1.getOverall().split("-")[0]);
                    int wins2 = Integer.parseInt(s2.getOverall().split("-")[0]);
                    return Integer.compare(wins2, wins1);
                })
                .map(standings -> new Ranking(
                        standings.getSeed(),
                        standings.getTeam(),
                        standings.getOverall()))
                .collect(Collectors.toList());
    }

}
