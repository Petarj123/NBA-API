package com.api.nba.DTO;

import com.api.nba.teams.Conference;

import java.util.List;

public record Teams(List<Conference> teams) {
}
