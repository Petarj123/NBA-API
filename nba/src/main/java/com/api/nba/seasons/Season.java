package com.api.nba.seasons;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seasons")
public class Season {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "SEASON")
    private String season;
    @Column(name = "LG")
    private String league;
    @Column(name = "CHAMPION")
    private String champion;
    @Column(name = "MVP")
    private String mvp;
    @Column(name = "DPOY")
    private String defensivePlayerOfTheYear;
    @Column(name = "SMOY")
    private String sixthManOfTheYear;
    @Column(name = "ROY")
    private String rookieOfTheYear;
    @Column(name = "MIP")
    private String mostImproved;
    @Column(name = "PTS")
    private String points;
    @Column(name = "RBS")
    private String rebounds;
    @Column(name = "AST")
    private String assists;
    @Column(name = "WIN_SHARES")
    private String winShares;
}