package com.api.nba.players.controller;

import com.api.nba.DTO.PlayerData;
import com.api.nba.exceptions.PlayerNotFoundException;
import com.api.nba.players.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerData> returnPlayerRegularSeasons(@RequestParam String firstName, @RequestParam String lastName) throws PlayerNotFoundException {
        return playerService.retrievePlayerRegularSeasons(firstName, lastName);
    }
    @GetMapping("/playoff")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerData> returnPlayerPlayoffs(@RequestParam String firstName, @RequestParam String lastName) throws PlayerNotFoundException {
        return playerService.retrievePlayerPlayoffSeasons(firstName, lastName);
    }
}
