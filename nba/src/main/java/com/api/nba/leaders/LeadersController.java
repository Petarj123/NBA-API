package com.api.nba.leaders;

import com.api.nba.DTO.AssistsLeader;
import com.api.nba.DTO.PointsLeader;
import com.api.nba.DTO.ReboundsLeader;
import com.api.nba.DTO.StealsLeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaders")
@RequiredArgsConstructor
public class LeadersController {

    private final LeadersService leadersService;

    @GetMapping("/pts")
    @ResponseStatus(HttpStatus.OK)
    public List<PointsLeader> getScoringLeaders(@RequestParam(required = false) Integer top){
        return leadersService.scoringLeadersList(top);
    }
    @GetMapping("/ast")
    @ResponseStatus(HttpStatus.OK)
    public List<AssistsLeader> getAssistsLeaders(@RequestParam(required = false) Integer top){
        return leadersService.assistsLeaderList(top);
    }
    @GetMapping("/stl")
    @ResponseStatus(HttpStatus.OK)
    public List<StealsLeader> getStealsLeaders(@RequestParam(required = false) Integer top){
        return leadersService.stealsLeaderList(top);
    }
    @GetMapping("/reb")
    @ResponseStatus(HttpStatus.OK)
    public List<ReboundsLeader> getReboundingLeaders(@RequestParam(required = false) Integer top){
        return leadersService.reboundsLeaderList(top);
    }

}
