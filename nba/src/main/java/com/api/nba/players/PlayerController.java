package com.api.nba.players;

import com.api.nba.DTO.PlayerData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public PlayerData returnPlayerData(@RequestParam String firstName, @RequestParam String lastName){
        return playerService.retrievePlayerSeasons(firstName, lastName);
    }
}
