package com.api.nba.teams;

import com.api.nba.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/conference")
    @ResponseStatus(HttpStatus.OK)
    public ConferenceTeams getConferenceTeams(@RequestParam String conference){
        return teamService.getConferenceTeams(conference);
    }
    @GetMapping("/division")
    @ResponseStatus(HttpStatus.OK)
    public DivisionTeams getDivisionTeams(@RequestParam String division){
        return teamService.getDivisionTeams(division);
    }
    @GetMapping("/standings")
    @ResponseStatus(HttpStatus.OK)
    public StandingsSimple getStandings(@RequestParam String season){
        return teamService.getStandings(season);
    }
    @GetMapping("/standings/conference")
    @ResponseStatus(HttpStatus.OK)
    public DivisionRankings getConferenceStandings(@RequestParam String season){
        return teamService.getConferenceStandings(season);
    }
    @GetMapping("/standings/divisions")
    @ResponseStatus(HttpStatus.OK)
    public DivisionRankings getDivisionStandings(@RequestParam String season){
        return teamService.getDivisionStandings(season);
    }
    @GetMapping("/h2h")
    @ResponseStatus(HttpStatus.OK)
    public HeadToHead getDivisionStandings(@RequestParam String team1, @RequestParam String team2){
        return teamService.getHeadToHeadScore(team1, team2);
    }
}
