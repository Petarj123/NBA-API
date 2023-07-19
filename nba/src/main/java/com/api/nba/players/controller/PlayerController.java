package com.api.nba.players.controller;

import com.api.nba.DTO.PlayerData;
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

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerData> returnPlayerData(@RequestParam String firstName, @RequestParam String lastName){
        return playerService.retrievePlayerSeasons(firstName, lastName);
    }
}
