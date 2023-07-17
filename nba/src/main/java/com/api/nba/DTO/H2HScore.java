package com.api.nba.DTO;

import java.util.Map;

public record H2HScore(String season, Map<String, String> team1, Map<String, String> team2) {
}
