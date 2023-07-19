package com.api.nba.leaders.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "points_leaders_alltime")
public class Points {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "'rank'")
    private String rank;
    private String playerName;
    private Integer points;
}
