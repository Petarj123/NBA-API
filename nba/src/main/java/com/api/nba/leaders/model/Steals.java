package com.api.nba.leaders.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "steals_leaders_alltime")
public class Steals {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "'rank'")
    private String rank;
    private String playerName;
    private Integer steals;
}
