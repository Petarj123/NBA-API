package com.api.nba.teams.controller;

import com.api.nba.DTO.*;
import com.api.nba.exceptions.IllegalAbbreviationException;
import com.api.nba.exceptions.InvalidConferenceException;
import com.api.nba.exceptions.InvalidDivisionException;
import com.api.nba.exceptions.InvalidSeasonException;
import com.api.nba.teams.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/allTeams")
    @ResponseStatus(HttpStatus.OK)
    public Teams getAllTeams(){
        return teamService.getAllTeams();
    }
    @GetMapping("/conference/{conference}")
    @ResponseStatus(HttpStatus.OK)
    public ConferenceTeams getConferenceTeams(@PathVariable String conference) throws InvalidConferenceException {
        return teamService.getConferenceTeams(conference);
    }

    @GetMapping("/division/{division}")
    @ResponseStatus(HttpStatus.OK)
    public DivisionTeams getDivisionTeams(@PathVariable String division) throws InvalidDivisionException {
        return teamService.getDivisionTeams(division);
    }

    @GetMapping("/{season}/standings")
    @ResponseStatus(HttpStatus.OK)
    public StandingsSimple getStandings(@PathVariable String season) throws InvalidSeasonException {
        return teamService.getStandings(season);
    }

    @GetMapping("/{season}/standings/conference")
    @ResponseStatus(HttpStatus.OK)
    public DivisionRankings getConferenceStandings(@PathVariable String season) throws InvalidSeasonException {
        return teamService.getConferenceStandings(season);
    }

    @GetMapping("/{season}/standings/divisions")
    @ResponseStatus(HttpStatus.OK)
    public DivisionRankings getDivisionStandings(@PathVariable String season) throws InvalidSeasonException {
        return teamService.getDivisionStandings(season);
    }
    @GetMapping("/h2h/{team1}/{team2}")
    @ResponseStatus(HttpStatus.OK)
    public HeadToHead getHeadToHeadScore(@PathVariable String team1, @PathVariable String team2) throws IllegalAbbreviationException {
        return teamService.getHeadToHeadScore(team1, team2);
    }
    @GetMapping("{team}/standings/advanced")
    @ResponseStatus(HttpStatus.OK)
    public List<StandingsAdvanced> getTeamSeasonStats(@PathVariable String team, @RequestParam(required = false) String season) throws InvalidSeasonException, IllegalAbbreviationException {
        return teamService.getTeamSeasonStats(team, season);
    }
}
