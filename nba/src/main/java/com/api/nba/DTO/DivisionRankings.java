package com.api.nba.DTO;

import java.util.List;
import java.util.Map;

public record DivisionRankings(String season, List<Map<String, List<Ranking>>> rankings) {
}
