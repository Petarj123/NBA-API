package com.api.nba.DTO;

import com.api.nba.teams.model.Conference;

import java.util.List;

public record Teams(List<Conference> teams) {
}
