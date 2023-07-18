package com.api.nba.leaders;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "assists_leaders_alltime")
public class Assists {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "'rank'")
    private String rank;
    private String playerName;
    private Integer assists;
}
