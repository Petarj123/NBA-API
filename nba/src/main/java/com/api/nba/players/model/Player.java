package com.api.nba.players.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "players_regular_season")
public class Player {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SEASON")
    private String season;
    @Column(name = "LG")
    private String league;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "TM")
    private String team;
    @Column(name = "POS")
    private String position;
    @Column(name = "GP")
    private Integer gamesPlayed;
    @Column(name = "GS")
    private Integer gamesStarted;
    @Column(name = "MPG")
    private Double minutesPerGame;
    @Column(name = "FG")
    private Double fieldGoals;
    @Column(name = "FGA")
    private Double fieldGoalAttempts;
    @Column(name = "FG%")
    private Double fieldGoalPercentage;
    @Column(name = "3P")
    private Double threePointFieldGoals;
    @Column(name = "3PA")
    private Double threePointFieldGoalAttempts;
    @Column(name = "3P%")
    private Double threePointFieldGoalPercentage;
    @Column(name = "2P")
    private Double twoPointFieldGoals;
    @Column(name = "2PA")
    private Double twoPointFieldGoalAttempts;
    @Column(name = "2P%")
    private Double twoPointFieldGoalPercentage;
    @Column(name = "eFG%")
    private Double effectiveFieldGoalPercentage;
    @Column(name = "FT")
    private Double freeThrows;
    @Column(name = "FTA")
    private Double freeThrowAttempts;
    @Column(name = "FTP")
    private Double freeThrowPercentage;
    @Column(name = "ORB")
    private Double offensiveRebounds;
    @Column(name = "DRB")
    private Double defensiveRebounds;
    @Column(name = "TRB")
    private Double totalRebounds;
    @Column(name = "AST")
    private Double assists;
    @Column(name = "STL")
    private Double steals;
    @Column(name = "BLK")
    private Double blocks;
    @Column(name = "TOV")
    private Double turnovers;
    @Column(name = "PF")
    private Double personalFouls;
    @Column(name = "PTS")
    private Double points;
}