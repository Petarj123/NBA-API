package com.api.nba.service;

import com.api.nba.DTO.Champion;
import com.api.nba.DTO.PlayerAward;
import com.api.nba.exceptions.InvalidSeasonException;
import com.api.nba.seasons.model.Season;
import com.api.nba.seasons.repository.SeasonRepository;
import com.api.nba.seasons.service.SeasonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeasonServiceTest {
    @Mock
    private SeasonRepository seasonRepository;
    @InjectMocks
    private SeasonService seasonService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Season season;
    @BeforeEach
    void setUp() {
        season = Season.builder()
                .id(1L)
                .season("2015-2016")
                .league("NBA")
                .champion("Cleveland Cavaliers")
                .mvp("Stephen Curry")
                .defensivePlayerOfTheYear("Kawhi Leonard")
                .sixthManOfTheYear("Jamal Crawford")
                .rookieOfTheYear("Karl-Anthony Towns")
                .mostImproved("C.J. McCollum")
                .points("ActualScoringChampion")
                .rebounds("ActualReboundingChampion")
                .assists("ActualAssistsChampion")
                .winShares("Lebron James")
                .build();
    }

    @Test
    void testGetChampionSuccess() throws InvalidSeasonException, JsonProcessingException {
        when(seasonRepository.findBySeason("2015-2016")).thenReturn(Optional.ofNullable(season));

        Champion champion = seasonService.getChampion("2015-2016");
        assertNotNull(champion);

        Champion expectedChampion = new Champion("2015-2016", "Cleveland Cavaliers");
        assertEquals(objectMapper.writeValueAsString(expectedChampion), objectMapper.writeValueAsString(champion));
    }

    @Test
    void getMvpSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "MVP", season1 -> {
            try {
                return seasonService.getMvp(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "Stephen Curry");
    }
    @Test
    void getRoySuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Rookie of the Year", season1 -> {
            try {
                return seasonService.getRoy(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "Karl-Anthony Towns");
    }

    @Test
    void getScoringChampionSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Scoring Champion", season1 -> {
            try {
                return seasonService.getScoringChampion(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "ActualScoringChampion");
    }

    @Test
    void getAssistsChampionSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Assists Champion", season1 -> {
            try {
                return seasonService.getAssistsChampion(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "ActualAssistsChampion");
    }

    @Test
    void getReboundingChampionSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Rebounding Champion", season1 -> {
            try {
                return seasonService.getReboundingChampion(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "ActualReboundingChampion");
    }

    @Test
    void getPlayerWithHighestWinSharesSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Player with Highest Win Shares", season1 -> {
            try {
                return seasonService.getPlayerWithHighestWinShares(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "Lebron James");
    }

    @Test
    void getDefensivePlayerOfTheYearSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Defensive Player of the Year", season1 -> {
            try {
                return seasonService.getDefensivePlayerOfTheYear(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "Kawhi Leonard");
    }

    @Test
    void getSixthManOfTheYearSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Sixth Man of the Year", season1 -> {
            try {
                return seasonService.getSixthManOfTheYear(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "Jamal Crawford");
    }

    @Test
    void getMostImprovedPlayerSuccess() throws JsonProcessingException {
        testAwardSuccess("2015-2016", "Most Improved Player", season1 -> {
            try {
                return seasonService.getMostImprovedPlayer(season1);
            } catch (InvalidSeasonException e) {
                throw new RuntimeException(e);
            }
        }, "C.J. McCollum");
    }

    @Test
    void testMethodButWithInvalidSeason() {
        // Set up the mock to return an Optional
        when(seasonRepository.findBySeason(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            if ("2015-2016".equals(argument)) {
                return Optional.of(season);
            } else {
                return Optional.empty();
            }
        });

        assertThrows(InvalidSeasonException.class,
                () -> seasonService.getChampion("2014-2015"));
    }
    // Works for all awards
    @Test
    void checkIfSeasonIsNotAvailableShouldThrowInvalidSeasonExceptionAndCorrectMessage() {
        when(seasonRepository.findBySeason("2025-2026")).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                InvalidSeasonException.class,
                () -> seasonService.getChampion("2025-2026")
        );

        String expectedMessage = "The season 2025-2026 is not available";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    private void testAwardSuccess(String seasonYear, String award, Function<String, PlayerAward> method, String expectedWinner) throws JsonProcessingException {
        when(seasonRepository.findBySeason(seasonYear)).thenReturn(Optional.ofNullable(season));

        PlayerAward playerAward = method.apply(seasonYear);
        assertNotNull(playerAward);

        PlayerAward expectedAward = new PlayerAward(seasonYear, expectedWinner);
        assertEquals(objectMapper.writeValueAsString(expectedAward), objectMapper.writeValueAsString(playerAward));
    }
}
