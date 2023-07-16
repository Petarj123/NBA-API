package com.api.nba.seasons;

import com.api.nba.DTO.*;
import com.api.nba.exceptions.InvalidSeasonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seasons")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/champion")
    @ResponseStatus(HttpStatus.OK)
    public Champion getChampion(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getChampion(season);
    }
    @GetMapping("/mvp")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getMvp(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getMvp(season);
    }
    @GetMapping("/dpoy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getDpoy(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getDefensivePlayerOfTheYear(season);
    }
    @GetMapping("/smoy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getSmoy(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getSixthManOfTheYear(season);
    }
    @GetMapping("/mip")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getMip(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getMostImprovedPlayer(season);
    }
    @GetMapping("/roy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getROY(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getRoy(season);
    }
    @GetMapping("/assist")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getAssistsChampion(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getAssistsChampion(season);
    }
    @GetMapping("/scoring")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getScoringChampion(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getScoringChampion(season);
    }
    @GetMapping("/rebound")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getReboundingChampion(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getReboundingChampion(season);
    }
    @GetMapping("/ws")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getPlayerWithHighestWinShares(@RequestParam String season) throws InvalidSeasonException {
        return seasonService.getPlayerWithHighestWinShares(season);
    }
}
