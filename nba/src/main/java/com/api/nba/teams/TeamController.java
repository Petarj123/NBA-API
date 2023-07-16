package com.api.nba.teams;

import com.api.nba.DTO.ConferenceTeams;
import com.api.nba.DTO.DivisionTeams;
import com.api.nba.DTO.Teams;
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
}
