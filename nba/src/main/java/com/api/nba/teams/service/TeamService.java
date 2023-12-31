package com.api.nba.teams.service;

import com.api.nba.DTO.*;
import com.api.nba.exceptions.IllegalAbbreviationException;
import com.api.nba.exceptions.InvalidConferenceException;
import com.api.nba.exceptions.InvalidDivisionException;
import com.api.nba.exceptions.InvalidSeasonException;
import com.api.nba.teams.model.Conference;
import com.api.nba.teams.model.StandingsPerSeason;
import com.api.nba.teams.model.TeamVsTeam;
import com.api.nba.teams.repository.ConferenceRepository;
import com.api.nba.teams.repository.StandingsPerSeasonRepository;
import com.api.nba.teams.repository.TeamVsTeamRepository;
import org.springframework.dao.DataAccessException;
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
    private final Map<String, Supplier<List<String>>> divisionMap = new HashMap<>();
    private List<String> westernConferenceTeamNames = new ArrayList<>();
    private List<String> easternConferenceTeamNames = new ArrayList<>();
    public TeamService(ConferenceRepository conferenceRepository, StandingsPerSeasonRepository standingsPerSeasonRepository, TeamVsTeamRepository teamVsTeamRepository) {
        this.conferenceRepository = conferenceRepository;
        this.standingsPerSeasonRepository = standingsPerSeasonRepository;
        this.teamVsTeamRepository = teamVsTeamRepository;
        initializeTeamNames();
        initializeConferences();
        initializeDivisions();
    }

    public Teams getAllTeams() {
        try {
            List<Conference> teams = conferenceRepository.findAll();
            return new Teams(teams);
        } catch (DataAccessException e) {
            // Log the exception details and potentially notify
            throw new RuntimeException("Could not retrieve teams from the database", e);
            // Or return some default value, though this depends on how you want such scenarios handled in your application
        }
    }

    public ConferenceTeams getConferenceTeams(String conference) throws InvalidConferenceException {
        try {
            if (conference.equalsIgnoreCase("west")){
                return new ConferenceTeams("West", conferenceRepository.findWesternConferenceTeamNames());
            }
            else if (conference.equalsIgnoreCase("east")){
                return new ConferenceTeams("East", conferenceRepository.findEasternConferenceTeamNames());
            }
            throw new InvalidConferenceException("Invalid conference: '" + conference + "'. Conference can only be 'east' or 'west'");
        } catch (DataAccessException e) {
            throw e;
        }
    }
    public DivisionTeams getDivisionTeams(String division) throws InvalidDivisionException {
        String fullDivisionName = getFullDivisionName(division);

        Supplier<List<String>> divisionSupplier = divisionMap.get(division.toLowerCase());

        List<String> teams = divisionSupplier.get();
        return new DivisionTeams(fullDivisionName, teams);
    }
    public StandingsSimple getStandings(String season) throws InvalidSeasonException {
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);

        if (list.isEmpty()){
            throw new InvalidSeasonException("Invalid season: " + season);
        }
        List<Ranking> resultList = list.stream().map(team -> new Ranking(team.getSeed(), team.getTeam(), team.getOverall())).toList();
        return new StandingsSimple(season, resultList);
    }
    public DivisionRankings getConferenceStandings(String season) throws InvalidSeasonException {
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);

        if (list.isEmpty()){
            throw new InvalidSeasonException("Invalid season: " + season);
        }

        Map<String, Supplier<List<String>>> conferenceMap = new HashMap<>();
        conferenceMap.put("Western Conference", () -> westernConferenceTeamNames);
        conferenceMap.put("Eastern Conference", () -> easternConferenceTeamNames);

        List<Map<String, List<Ranking>>> finalResult = new ArrayList<>();

        for (Map.Entry<String, Supplier<List<String>>> entry : conferenceMap.entrySet()) {
            finalResult.add(Collections.singletonMap(entry.getKey(), processStandings(list, entry.getValue().get())));
        }

        return new DivisionRankings(season, finalResult);
    }

    public DivisionRankings getDivisionStandings(String season) throws InvalidSeasonException {
        List<StandingsPerSeason> list = standingsPerSeasonRepository.findBySeason(season);

        if (list.isEmpty()){
            throw new InvalidSeasonException("Invalid season: " + season);
        }

        List<Map<String, List<Ranking>>> finalResult = new ArrayList<>();

        for (Map.Entry<String, Supplier<List<String>>> entry : divisionMap.entrySet()) {
            finalResult.add(Collections.singletonMap(entry.getKey(), processStandings(list, entry.getValue().get())));
        }

        return new DivisionRankings(season, finalResult);
    }
    public HeadToHead getHeadToHeadScore(String team1, String team2) throws IllegalAbbreviationException {
        if (team1.equalsIgnoreCase(team2)){
            throw new IllegalArgumentException("Teams can not be the same!");
        }
        if (!teamNames.containsKey(team1.toLowerCase()) || !teamNames.containsKey(team2.toLowerCase())) {
            throw new IllegalAbbreviationException("One or both team abbreviations not found");
        }
        String fullTeamName = getFullTeamName(team1.toLowerCase());
        String fullTeamName2 = getFullTeamName(team2.toLowerCase());
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
    public List<StandingsAdvanced> getTeamSeasonStats(String teamAbbreviation, String season) throws InvalidSeasonException, IllegalAbbreviationException {
        String team = getFullTeamName(teamAbbreviation);
        if (season == null){
            List<StandingsPerSeason> seasons = standingsPerSeasonRepository.findByTeam(team);
            return seasons.stream().map(this::mapToStandingsAdvanced).toList();
        }
        List<StandingsPerSeason> seasonStanding = standingsPerSeasonRepository.findBySeasonAndTeam(season, team);

        if(seasonStanding.isEmpty()) {
            throw new InvalidSeasonException("The provided season does not exist.");
        }

        return seasonStanding.stream().map(this::mapToStandingsAdvanced).toList();
    }

    private StandingsAdvanced mapToStandingsAdvanced(StandingsPerSeason s) {
        return new StandingsAdvanced(s.getSeason(), s.getTeam(), s.getSeed(),
                s.getOverall(), s.getHome(), s.getRoad(),
                s.getE(), s.getW(), s.getA(),
                s.getC(), s.getSE(), s.getNW(),
                s.getP(), s.getSW(), s.getPre(),
                s.getPost(), s.getWinLoseRecordLessThanEqualTo3(), s.getWinLoseRecordGreaterOrEqualTo10(),
                s.getOct(), s.getNov(), s.getDec(),
                s.getJan(), s.getFeb(), s.getMar(),
                s.getApr());
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
    private void initializeDivisions() {
        divisionMap.put("atlantic", conferenceRepository::findAtlanticDivisionTeamNames);
        divisionMap.put("central", conferenceRepository::findCentralDivisionTeamNames);
        divisionMap.put("pacific", conferenceRepository::findPacificDivisionTeamNames);
        divisionMap.put("sw", conferenceRepository::findSouthWestDivisionTeamNames);
        divisionMap.put("nw", conferenceRepository::findNorthWestDivisionTeamNames);
        divisionMap.put("se", conferenceRepository::findSouthEastDivisionTeamNames);
    }
    private void initializeConferences() {
        westernConferenceTeamNames = conferenceRepository.findWesternConferenceTeamNames();
        easternConferenceTeamNames = conferenceRepository.findEasternConferenceTeamNames();
    }
    private String getFullTeamName(String abbreviation) throws IllegalAbbreviationException {
        if (teamNames.get(abbreviation) == null){
            throw new IllegalAbbreviationException("Invalid abbreviation: " + abbreviation);
        }
        return teamNames.get(abbreviation);
    }
    private String getFullDivisionName(String abbreviation) throws InvalidDivisionException {
        return switch (abbreviation) {
            case "atlantic" -> "Atlantic";
            case "central" -> "Central";
            case "pacific" -> "Pacific";
            case "se" -> "Southeast";
            case "nw" -> "Northwest";
            case "sw" -> "Southwest";
            default ->
                    throw new InvalidDivisionException("Invalid division. Valid divisions are 'atlantic', 'central', 'se', 'nw', 'pacific', and 'sw'.");
        };
    }
}
