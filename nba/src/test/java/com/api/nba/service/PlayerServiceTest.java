package com.api.nba.service;

import com.api.nba.DTO.PlayerData;
import com.api.nba.DTO.PlayerStat;
import com.api.nba.exceptions.PlayerNotFoundException;
import com.api.nba.players.model.AllPlayers;
import com.api.nba.players.model.Player;
import com.api.nba.players.model.PlayoffPlayer;
import com.api.nba.players.repository.PlayerRepository;
import com.api.nba.players.repository.PlayoffPlayerRepository;
import com.api.nba.players.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayoffPlayerRepository playoffPlayerRepository;
    @InjectMocks
    private PlayerService playerService;
    private List<Player> mockPlayers;
    private List<PlayoffPlayer> mockPlayoffPlayers;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setUp() {
        AllPlayers allPlayer1 = new AllPlayers();

        Player player1 = Player.builder()
                .id(1L)
                .allPlayers(allPlayer1)
                .firstName("Michael")
                .lastName("Jordan")
                .season("1996-1997")
                .league("NBA")
                .team("CHI")
                .points(30.1)
                .build();
        PlayoffPlayer poPlayer1 = PlayoffPlayer.builder()
                .id(1L)
                .allPlayers(allPlayer1)
                .firstName("Michael")
                .lastName("Jordan")
                .season("1996-1997")
                .league("NBA")
                .team("CHI")
                .points(30.1)
                .build();

        AllPlayers allPlayer2 = new AllPlayers();

        Player player2 = Player.builder()
                .id(2L)
                .allPlayers(allPlayer2)
                .firstName("LeBron")
                .lastName("James")
                .season("2012-2013")
                .league("NBA")
                .team("MIA")
                .points(26.8)
                .build();
        PlayoffPlayer poPlayer2 = PlayoffPlayer.builder()
                .id(2L)
                .allPlayers(allPlayer2)
                .firstName("LeBron")
                .lastName("James")
                .season("2012-2013")
                .league("NBA")
                .team("MIA")
                .points(26.8)
                .build();

        mockPlayers = Arrays.asList(player1, player2);
        mockPlayoffPlayers = Arrays.asList(poPlayer1, poPlayer2);
        lenient().when(playerRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(mockPlayers);
        lenient().when(playoffPlayerRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(mockPlayoffPlayers);
    }

    @Test
    void testRetrieverPlayerSuccess() throws PlayerNotFoundException, JsonProcessingException {
        String firstName = "LeBron";
        String lastName = "James";

        playerService.retrievePlayerRegularSeasons(firstName, lastName);
        assertFalse(mockPlayers.isEmpty());
        Player lebron = mockPlayers.stream()
                .filter(player -> player.getFirstName().equals(firstName) && player.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("Test setup player is not found"));

        assertTrue(mockPlayers.contains(lebron));
        assertFalse(lebron.getSeason().isBlank());
        assertEquals("2012-2013", lebron.getSeason(), "Incorrect season");
        assertEquals("NBA", lebron.getLeague(), "Incorrect league");
        assertEquals("MIA", lebron.getTeam(), "Incorrect team");
        assertEquals(26.8, lebron.getPoints(), "Incorrect ppg");

        PlayerStat playerStat = PlayerStat.builder()
                .season(lebron.getSeason())
                .team(lebron.getTeam())
                .points(lebron.getPoints())
                .league(lebron.getLeague())
                .build();

        List<PlayerStat> list = new ArrayList<>();
        list.add(playerStat);

        PlayerData pd = new PlayerData(firstName + " " + lastName, list);

        String expected = "{\"playerName\":\"LeBron James\",\"stats\":[{\"season\":\"2012-2013\",\"league\":\"NBA\",\"age\":null,\"team\":\"MIA\",\"position\":null,\"gamesPlayed\":null,\"gamesStarted\":null,\"minutesPerGame\":null,\"fieldGoals\":null,\"fieldGoalAttempts\":null,\"fieldGoalPercentage\":null,\"threePointFieldGoals\":null,\"threePointFieldGoalAttempts\":null,\"threePointFieldGoalPercentage\":null,\"twoPointFieldGoals\":null,\"twoPointFieldGoalAttempts\":null,\"twoPointFieldGoalPercentage\":null,\"effectiveFieldGoalPercentage\":null,\"freeThrows\":null,\"freeThrowAttempts\":null,\"freeThrowPercentage\":null,\"offensiveRebounds\":null,\"defensiveRebounds\":null,\"totalRebounds\":null,\"assists\":null,\"steals\":null,\"blocks\":null,\"turnovers\":null,\"personalFouls\":null,\"points\":26.8}]}";

        assertEquals(expected, objectMapper.writeValueAsString(pd));
    }
    @Test
    void testRetrievePlayersFailure() {
        String firstName = "Carmelo";
        String lastName = "Anthony";

        when(playerRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(
                PlayerNotFoundException.class,
                () -> playerService.retrievePlayerRegularSeasons(firstName, lastName),
                "Expected retrievePlayerRegularSeasons() to throw, but it didn't"
        );

        String expectedMessage = "No player found with the given first and last name.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message mismatch");
    }
    @Test
    void testRetrievePlayoffPlayerSuccess() throws PlayerNotFoundException, JsonProcessingException {
        String firstName = "LeBron";
        String lastName = "James";

        playerService.retrievePlayerPlayoffSeasons(firstName, lastName);

        assertFalse(mockPlayoffPlayers.isEmpty());

        PlayoffPlayer lebron = mockPlayoffPlayers.stream()
                .filter(player -> player.getFirstName().equals(firstName) && player.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test setup error: Player not found in mock data"));

        assertTrue(mockPlayoffPlayers.contains(lebron));
        assertFalse(lebron.getSeason().isBlank());
        assertEquals("2012-2013", lebron.getSeason(), "Incorrect season");
        assertEquals("NBA", lebron.getLeague(), "Incorrect league");
        assertEquals("MIA", lebron.getTeam(), "Incorrect team");
        assertEquals(26.8, lebron.getPoints(), "Incorrect ppg");

        PlayerStat playerStat = PlayerStat.builder()
                .season(lebron.getSeason())
                .team(lebron.getTeam())
                .points(lebron.getPoints())
                .league(lebron.getLeague())
                .build();
        List<PlayerStat> list = new ArrayList<>();
        list.add(playerStat);

        PlayerData pd = new PlayerData(firstName + " " + lastName, list);

        String expected = "{\"playerName\":\"LeBron James\",\"stats\":[{\"season\":\"2012-2013\",\"league\":\"NBA\",\"age\":null,\"team\":\"MIA\",\"position\":null,\"gamesPlayed\":null,\"gamesStarted\":null,\"minutesPerGame\":null,\"fieldGoals\":null,\"fieldGoalAttempts\":null,\"fieldGoalPercentage\":null,\"threePointFieldGoals\":null,\"threePointFieldGoalAttempts\":null,\"threePointFieldGoalPercentage\":null,\"twoPointFieldGoals\":null,\"twoPointFieldGoalAttempts\":null,\"twoPointFieldGoalPercentage\":null,\"effectiveFieldGoalPercentage\":null,\"freeThrows\":null,\"freeThrowAttempts\":null,\"freeThrowPercentage\":null,\"offensiveRebounds\":null,\"defensiveRebounds\":null,\"totalRebounds\":null,\"assists\":null,\"steals\":null,\"blocks\":null,\"turnovers\":null,\"personalFouls\":null,\"points\":26.8}]}";

        assertEquals(expected, objectMapper.writeValueAsString(pd));
    }


    @Test
    void testRetrievePlayerPlayoffSeasonsFailure() {
        String firstName = "Unknown";
        String lastName = "Player";

        when(playoffPlayerRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(
                PlayerNotFoundException.class,
                () -> playerService.retrievePlayerPlayoffSeasons(firstName, lastName),
                "Expected retrievePlayerPlayoffSeasons() to throw, but it didn't"
        );

        String expectedMessage = "No player found with the given first and last name.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message mismatch");
    }

}
