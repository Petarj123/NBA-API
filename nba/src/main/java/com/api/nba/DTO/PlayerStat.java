package com.api.nba.DTO;

import lombok.Builder;

@Builder
public record PlayerStat(
        String season,
        String league,
        Integer age,
        String team,
        String position,
        Integer gamesPlayed,
        Integer gamesStarted,
        Double minutesPerGame,
        Double fieldGoals,
        Double fieldGoalAttempts,
        Double fieldGoalPercentage,
        Double threePointFieldGoals,
        Double threePointFieldGoalAttempts,
        Double threePointFieldGoalPercentage,
        Double twoPointFieldGoals,
        Double twoPointFieldGoalAttempts,
        Double twoPointFieldGoalPercentage,
        Double effectiveFieldGoalPercentage,
        Double freeThrows,
        Double freeThrowAttempts,
        Double freeThrowPercentage,
        Double offensiveRebounds,
        Double defensiveRebounds,
        Double totalRebounds,
        Double assists,
        Double steals,
        Double blocks,
        Double turnovers,
        Double personalFouls,
        Double points
) {}
