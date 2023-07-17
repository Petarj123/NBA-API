package com.api.nba.DTO;

import java.util.List;

public record StandingsSimple(String season, List<Ranking> rankings) {
}
