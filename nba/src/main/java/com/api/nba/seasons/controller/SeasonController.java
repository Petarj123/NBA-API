package com.api.nba.seasons.controller;

import com.api.nba.DTO.*;
import com.api.nba.exceptions.InvalidSeasonException;
import com.api.nba.seasons.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seasons")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/{season}/champion")
    @ResponseStatus(HttpStatus.OK)
    public Champion getChampion(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getChampion(season);
    }

    @GetMapping("/{season}/mvp")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getMvp(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getMvp(season);
    }

    @GetMapping("/{season}/dpoy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getDpoy(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getDefensivePlayerOfTheYear(season);
    }

    @GetMapping("/{season}/smoy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getSmoy(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getSixthManOfTheYear(season);
    }

    @GetMapping("/{season}/mip")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getMip(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getMostImprovedPlayer(season);
    }

    @GetMapping("/{season}/roy")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getRoy(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getRoy(season);
    }

    @GetMapping("/{season}/assist")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getAssistsChampion(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getAssistsChampion(season);
    }

    @GetMapping("/{season}/scoring")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getScoringChampion(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getScoringChampion(season);
    }

    @GetMapping("/{season}/rebound")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getReboundingChampion(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getReboundingChampion(season);
    }

    @GetMapping("/{season}/ws")
    @ResponseStatus(HttpStatus.OK)
    public PlayerAward getPlayerWithHighestWinShares(@PathVariable String season) throws InvalidSeasonException {
        return seasonService.getPlayerWithHighestWinShares(season);
    }
}
