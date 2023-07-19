package com.api.nba.seasons.service;

import com.api.nba.DTO.*;
import com.api.nba.exceptions.InvalidSeasonException;
import com.api.nba.seasons.model.Season;
import com.api.nba.seasons.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public Champion getChampion(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new Champion(season, result.getChampion());
    }
    public PlayerAward getMvp(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getMvp());
    }
    public PlayerAward getRoy(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getRookieOfTheYear());
    }
    public PlayerAward getScoringChampion(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getPoints());
    }
    public PlayerAward getAssistsChampion(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getAssists());
    }
    public PlayerAward getReboundingChampion(String season) throws InvalidSeasonException{
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getRebounds());
    }
   public PlayerAward getPlayerWithHighestWinShares(String season) throws InvalidSeasonException {
       Season result = seasonRepository.findBySeason(season);
       if (result == null){
           throw new InvalidSeasonException("The season " + season + "is not available");
       }
       return new PlayerAward(season, result.getWinShares());
   }
   public PlayerAward getDefensivePlayerOfTheYear(String season) throws InvalidSeasonException {
       Season result = seasonRepository.findBySeason(season);
       if (result == null){
           throw new InvalidSeasonException("The season " + season + "is not available");
       }
       return new PlayerAward(season, result.getDefensivePlayerOfTheYear());
   }
   public PlayerAward getSixthManOfTheYear(String season) throws InvalidSeasonException {
       Season result = seasonRepository.findBySeason(season);
       if (result == null){
           throw new InvalidSeasonException("The season " + season + "is not available");
       }
       return new PlayerAward(season, result.getSixthManOfTheYear());
   }
    public PlayerAward getMostImprovedPlayer(String season) throws InvalidSeasonException {
        Season result = seasonRepository.findBySeason(season);
        if (result == null){
            throw new InvalidSeasonException("The season " + season + "is not available");
        }
        return new PlayerAward(season, result.getMostImproved());
    }
}
