package com.api.nba.teams;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "standings_per_season")
public class StandingsPerSeason {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String seed;
    private String season;
    private String team;
    private String overall;
    @Column(name = "`home`")
    private String home;
    @Column(name = "`road`")
    private String road;
    @Column(name = "`E`")
    private String E;
    @Column(name = "`W`")
    private String W;
    @Column(name = "`A`")
    private String A;
    @Column(name = "`C`")
    private String C;
    @Column(name = "`SE`")
    private String SE;
    @Column(name = "`NW`")
    private String NW;
    @Column(name = "`P`")
    private String P;
    @Column(name = "`SW`")
    private String SW;
    @Column(name = "`pre`")
    private String pre;
    @Column(name = "`post`")
    private String post;
    @Column(name = "lte3")
    private String winLoseRecordLessThanEqualTo3;
    @Column(name = "gte10")
    private String winLoseRecordGreaterOrEqualTo10;
    @Column(name = "`oct`")
    private String oct;
    @Column(name = "`nov`")
    private String nov;
    @Column(name = "`dec`")
    private String dec;
    @Column(name = "`jan`")
    private String jan;
    @Column(name = "`feb`")
    private String feb;
    @Column(name = "`mar`")
    private String mar;
    @Column(name = "`apr`")
    private String apr;
}
