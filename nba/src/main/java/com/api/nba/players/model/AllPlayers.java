package com.api.nba.players.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "all_players")
public class AllPlayers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "'FROM'")
    private String from;
    @Column(name = "'TO'")
    private String to;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Column(name = "POSITION")
    private String position;
    @Column(name = "HEIGHT")
    private Integer height;
    @Column(name = "WEIGHT")
    private Integer weight;

    @OneToMany(mappedBy = "allPlayers")
    private List<Player> playerSeasons;

    @OneToMany(mappedBy = "allPlayers")
    private List<PlayoffPlayer> playoffPlayers;
}