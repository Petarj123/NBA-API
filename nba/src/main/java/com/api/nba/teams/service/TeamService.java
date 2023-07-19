package com.api.nba.teams.service;

import com.api.nba.DTO.*;
import com.api.nba.teams.model.Conference;
import com.api.nba.teams.model.StandingsPerSeason;
import com.api.nba.teams.model.TeamVsTeam;
import com.api.nba.teams.repository.ConferenceRepository;
import com.api.nba.teams.repository.StandingsPerSeasonRepository;
import com.api.nba.teams.repository.TeamVsTeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final ConferenceRepository conferenceRepository;
    private final StandingsPerSeasonRepository standingsPerSeasonRepository;
    private final TeamVsTeamRepository teamVsTeamRepository;
    private final Map<String, String> teamNames = new HashMap<>();

    public TeamService(ConferenceRepository conferenceRepository, StandingsPerSeasonRepository standingsPerSeasonRepository, TeamVsTeamRepository teamVsTeamRepository) {
        this.conferenceRepository = conferenceRepository;
        this.standingsPerSeasonRepository = standingsPerSeasonRepository;
        this.teamVsTeamRepository = teamVsTeamRepository;
        initializeTeamNames();
    }

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
    public HeadToHead getHeadToHeadScore(String team1, String team2) {
        if (!teamNames.containsKey(team1.toLowerCase()) || !teamNames.containsKey(team2.toLowerCase())) {
            throw new IllegalArgumentException("One or both team abbreviations not found");
        }
        String fullTeamName = getFullName(team1.toLowerCase());
        String fullTeamName2 = getFullName(team2.toLowerCase());
        List<TeamVsTeam> allGames = teamVsTeamRepository.findByTeam(fullTeamName);

        List<H2HScore> scores = new ArrayList<>();
        for (TeamVsTeam team : allGames){
            String team1Score = getTeamScore(team, team2);
            if (team1Score != null && !team1Score.isEmpty()) {
                Map<String, String> team1ScoreMap = Map.of(fullTeamName, team1Score);
                String team2Score = new StringBuilder(team1Score).reverse().toString();
                Map<String, String> team2ScoreMap = Map.of(fullTeamName2, team2Score);
                scores.add(new H2HScore(team.getSeason(), team1ScoreMap, team2ScoreMap));
            }
        }
        return new HeadToHead(scores);
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
    private String getTeamScore(TeamVsTeam team1, String opponent) {
        return switch (opponent.toLowerCase()) {
            case "atl" -> team1.getAtl();
            case "bos" -> team1.getBos();
            case "brk" -> team1.getBrk();
            case "chi" -> team1.getChi();
            case "cho" -> team1.getCho();
            case "cle" -> team1.getCle();
            case "dal" -> team1.getDal();
            case "den" -> team1.getDen();
            case "det" -> team1.getDet();
            case "gsw" -> team1.getGsw();
            case "hou" -> team1.getHou();
            case "ind" -> team1.getInd();
            case "lac" -> team1.getLac();
            case "lal" -> team1.getLal();
            case "mem" -> team1.getMem();
            case "mia" -> team1.getMia();
            case "mil" -> team1.getMil();
            case "min" -> team1.getMin();
            case "nop" -> team1.getNop();
            case "nyk" -> team1.getNyk();
            case "okc" -> team1.getOkc();
            case "orl" -> team1.getOrl();
            case "phi" -> team1.getPhi();
            case "pho" -> team1.getPho();
            case "por" -> team1.getPor();
            case "sac" -> team1.getSac();
            case "sas" -> team1.getSas();
            case "tor" -> team1.getTor();
            case "uta" -> team1.getUta();
            case "was" -> team1.getWas();
            default -> null;
        };
    }

    private void initializeTeamNames() {
        teamNames.put("atl", "Atlanta Hawks");
        teamNames.put("bos", "Boston Celtics");
        teamNames.put("brk", "Brooklyn Nets");
        teamNames.put("chi", "Chicago Bulls");
        teamNames.put("cho", "Charlotte Hornets");
        teamNames.put("cle", "Cleveland Cavaliers");
        teamNames.put("dal", "Dallas Mavericks");
        teamNames.put("den", "Denver Nuggets");
        teamNames.put("det", "Detroit Pistons");
        teamNames.put("gsw", "Golden State Warriors");
        teamNames.put("hou", "Houston Rockets");
        teamNames.put("ind", "Indiana Pacers");
        teamNames.put("lac", "Los Angeles Clippers");
        teamNames.put("lal", "Los Angeles Lakers");
        teamNames.put("mem", "Memphis Grizzlies");
        teamNames.put("mia", "Miami Heat");
        teamNames.put("mil", "Milwaukee Bucks");
        teamNames.put("min", "Minnesota Timberwolves");
        teamNames.put("nop", "New Orleans Pelicans");
        teamNames.put("nyk", "New York Knicks");
        teamNames.put("okc", "Oklahoma City Thunder");
        teamNames.put("orl", "Orlando Magic");
        teamNames.put("phi", "Philadelphia 76ers");
        teamNames.put("pho", "Phoenix Suns");
        teamNames.put("por", "Portland Trail Blazers");
        teamNames.put("sac", "Sacramento Kings");
        teamNames.put("sas", "San Antonio Spurs");
        teamNames.put("tor", "Toronto Raptors");
        teamNames.put("uta", "Utah Jazz");
        teamNames.put("was", "Washington Wizards");
    }

    public String getFullName(String abbreviation) {
        return teamNames.get(abbreviation);
    }
}
