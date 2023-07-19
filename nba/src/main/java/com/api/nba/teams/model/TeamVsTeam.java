package com.api.nba.teams.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "team_vs_team")
public class TeamVsTeam {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String season;
    private String seed;
    private String team;
    private String atl;
    private String bos;
    private String brk;
    private String chi;
    private String cho;
    private String cle;
    private String dal;
    private String den;
    private String det;
    private String gsw;
    private String hou;
    private String ind;
    private String lac;
    private String lal;
    private String mem;
    private String mia;
    private String mil;
    private String min;
    private String nop;
    private String nyk;
    private String okc;
    private String orl;
    private String phi;
    private String pho;
    private String por;
    private String sac;
    private String sas;
    private String tor;
    private String uta;
    private String was;
}