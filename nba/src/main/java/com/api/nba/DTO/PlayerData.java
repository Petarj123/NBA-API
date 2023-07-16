package com.api.nba.DTO;

import java.util.List;

public record PlayerData(String playerName, List<PlayerStat> stats) {
}
