package com.api.nba.service;

import com.api.nba.DTO.AssistsLeader;
import com.api.nba.DTO.PointsLeader;
import com.api.nba.DTO.ReboundsLeader;
import com.api.nba.DTO.StealsLeader;
import com.api.nba.exceptions.InvalidPlayerCountException;
import com.api.nba.leaders.model.Assists;
import com.api.nba.leaders.model.Points;
import com.api.nba.leaders.model.Rebounds;
import com.api.nba.leaders.model.Steals;
import com.api.nba.leaders.repository.AssistsRepository;
import com.api.nba.leaders.repository.PointsRepository;
import com.api.nba.leaders.repository.ReboundsRepository;
import com.api.nba.leaders.repository.StealsRepository;
import com.api.nba.leaders.service.LeadersService;
import com.api.nba.players.model.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeadersServiceTest {
    @Mock
    private StealsRepository stealsRepository;
    @Mock
    private ReboundsRepository reboundsRepository;
    @Mock
    private AssistsRepository assistsRepository;
    @Mock
    private PointsRepository pointsRepository;
    @InjectMocks
    private LeadersService leadersService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<Points> pointsList;
    private List<Assists> assistsList;
    private List<Rebounds> reboundsList;
    private List<Steals> stealsList;
    @BeforeEach
    void setUp() {
        pointsList = mockPoints();
        assistsList = mockAssists();
        reboundsList = mockRebounds();
        stealsList = mockSteals();
    }

    @Test
    void testScoringLeadersSuccess() throws InvalidPlayerCountException, JsonProcessingException {
        when(pointsRepository.findTopPlayers(5)).thenReturn(pointsList);

        List<PointsLeader> pointsLeaders = leadersService.scoringLeadersList(5); // Call the actual method

        assertNotNull(pointsLeaders);
        assertEquals(5, pointsLeaders.size());

        for (int i = 0; i < pointsLeaders.size(); i++) {
            PointsLeader leader = pointsLeaders.get(i);
            assertEquals("Firstname" + (5 - i) + " Lastname" + (5 - i), leader.player());
            assertEquals(5 - i, leader.points());
        }

        String actualJson = objectMapper.writeValueAsString(pointsLeaders);

        StringBuilder expectedJson = new StringBuilder("[");
        for (int i = 5; i >= 1; i--) {
            expectedJson.append(String.format("{\"rank\":\"%s\",\"player\":\"Firstname%d Lastname%d\",\"points\":%d}",
                    6 - i, i, i, i));
            if (i > 1) {
                expectedJson.append(",");
            }
        }
        expectedJson.append("]");

        assertEquals(expectedJson.toString(), actualJson);
    }

    @Test
    void testCorrectOrderWithTiedScores() throws InvalidPlayerCountException {
        List<Points> tiedPointsList = new ArrayList<>();
        // Creating two players with the same number of points.
        Points playerOne = new Points();
        playerOne.setPlayerName("Player One");
        playerOne.setPoints(20);
        tiedPointsList.add(playerOne);

        Points playerTwo = new Points();
        playerTwo.setPlayerName("Player Two");
        playerTwo.setPoints(20);
        tiedPointsList.add(playerTwo);

        when(pointsRepository.findTopPlayers(5)).thenReturn(tiedPointsList);

        List<PointsLeader> leaders = leadersService.scoringLeadersList(5);

        // Assuming that in the case of a tie, players are sorted alphabetically by name.
        assertEquals("Player One", leaders.get(0).player());
        assertEquals("Player Two", leaders.get(1).player());
    }

    @Test
    void testNumberOfPlayersIsOutOfBounds() {
        String exceptionMessage = "Number of players should be greater than zero and less than or equal to 250.";

        InvalidPlayerCountException thrownException = assertThrows(
                InvalidPlayerCountException.class,
                () -> leadersService.scoringLeadersList(260)
        );

        assertEquals(exceptionMessage, thrownException.getMessage());

        InvalidPlayerCountException thrownException2 = assertThrows(InvalidPlayerCountException.class, () -> leadersService.scoringLeadersList(-6));

        assertEquals(exceptionMessage, thrownException2.getMessage());
    }

    @Test
    void testBoundaryConditionsForPlayerCounts() {
        assertDoesNotThrow(() -> {
            leadersService.scoringLeadersList(1); // minimum valid number
        });

        assertThrows(InvalidPlayerCountException.class, () -> {
            leadersService.scoringLeadersList(0); // just below minimum
        });

        assertDoesNotThrow(() -> {
            leadersService.scoringLeadersList(250); // maximum valid number
        });

        assertThrows(InvalidPlayerCountException.class, () -> {
            leadersService.scoringLeadersList(251); // just above maximum
        });
    }

    @Test
    void testAssistLeadersListSuccess() throws InvalidPlayerCountException, JsonProcessingException {
        when(assistsRepository.findTopPlayers(5)).thenReturn(assistsList);

        List<AssistsLeader> assistsLeaders = leadersService.assistsLeaderList(5);

        assertNotNull(assistsLeaders);
        assertEquals(5, assistsLeaders.size());

        for (int i = 0; i < assistsLeaders.size(); i++) {
            AssistsLeader leader = assistsLeaders.get(i);
            assertEquals("Firstname" + (5 - i) + " Lastname" + (5 - i), leader.player());
            assertEquals(5 - i, leader.assists());
        }

        String actualJson = objectMapper.writeValueAsString(assistsLeaders);

        StringBuilder expectedJson = new StringBuilder("[");
        for (int i = 5; i >= 1; i--) {
            expectedJson.append(String.format("{\"rank\":\"%s\",\"player\":\"Firstname%d Lastname%d\",\"assists\":%d}",
                    6 - i, i, i, i));
            if (i > 1) {
                expectedJson.append(",");
            }
        }
        expectedJson.append("]");

        assertEquals(expectedJson.toString(), actualJson);
    }

    @Test
    void testReboundsLeaderList() throws InvalidPlayerCountException, JsonProcessingException {
        when(reboundsRepository.findTopPlayers(5)).thenReturn(reboundsList);

        List<ReboundsLeader> reboundsLeaders = leadersService.reboundsLeaderList(5);

        assertNotNull(reboundsLeaders);

        for (int i = 0; i < reboundsLeaders.size(); i++) {
            ReboundsLeader leader = reboundsLeaders.get(i);
            assertEquals("Firstname" + (5 - i) + " Lastname" + (5 - i), leader.player());
            assertEquals(5 - i, leader.rebounds());
        }

        String actualJson = objectMapper.writeValueAsString(reboundsLeaders);

        StringBuilder expectedJson = new StringBuilder("[");
        for (int i = 5; i >= 1; i--) {
            expectedJson.append(String.format("{\"rank\":\"%s\",\"player\":\"Firstname%d Lastname%d\",\"rebounds\":%d}",
                    6 - i, i, i, i));
            if (i > 1) {
                expectedJson.append(",");
            }
        }
        expectedJson.append("]");

        assertEquals(expectedJson.toString(), actualJson);
    }

    @Test
    void testStealsLeadersList() throws InvalidPlayerCountException, JsonProcessingException {
        when(stealsRepository.findTopPlayers(5)).thenReturn(stealsList);

        List<StealsLeader> stealsLeaders = leadersService.stealsLeaderList(5);
        assertNotNull(stealsLeaders);

        for (int i = 0; i < stealsLeaders.size(); i++) {
            StealsLeader leader = stealsLeaders.get(i);
            assertEquals("Firstname" + (5 - i) + " Lastname" + (5 - i), leader.player());
            assertEquals(5 - i, leader.steals());
        }

        String actualJson = objectMapper.writeValueAsString(stealsLeaders);

        StringBuilder expectedJson = new StringBuilder("[");
        for (int i = 5; i >= 1; i--) {
            expectedJson.append(String.format("{\"rank\":\"%s\",\"player\":\"Firstname%d Lastname%d\",\"steals\":%d}",
                    6 - i, i, i, i));
            if (i > 1) {
                expectedJson.append(",");
            }
        }
        expectedJson.append("]");

        assertEquals(expectedJson.toString(), actualJson);
    }


    private List<Points> mockPoints() {
        List<Points> result = new ArrayList<>();
        int rank = 1;
        for (int i = 5; i >= 1; i--) {
            Points point = new Points();
            point.setId((long) i);
            point.setRank(String.valueOf(rank));
            point.setPlayerName("Firstname" + i + " Lastname" + i);
            point.setPoints(i);
            result.add(point);
            rank++;
        }
        return result;
    }
    private List<Assists> mockAssists() {
        List<Assists> result = new ArrayList<>();
        int rank = 1;
        for (int i = 5; i >= 1; i--) {
            Assists assist = new Assists();
            assist.setId((long) i);
            assist.setRank(String.valueOf(rank));
            assist.setPlayerName("Firstname" + i + " Lastname" + i);
            assist.setAssists(i);
            result.add(assist);
            rank++;
        }
        return result;
    }
    private List<Rebounds> mockRebounds() {
        List<Rebounds> result = new ArrayList<>();
        int rank = 1;
        for (int i = 5; i >= 1; i--) {
            Rebounds rebound = new Rebounds();
            rebound.setId((long) i);
            rebound.setRank(String.valueOf(rank));
            rebound.setPlayerName("Firstname" + i + " Lastname" + i);
            rebound.setRebounds(i);
            result.add(rebound);
            rank++;
        }
        return result;
    }


    private List<Steals> mockSteals() {
        List<Steals> result = new ArrayList<>();
        int rank = 1;
        for (int i = 5; i >= 1; i--) {
            Steals steal = new Steals();
            steal.setId((long) i);
            steal.setRank(String.valueOf(rank));
            steal.setPlayerName("Firstname" + i + " Lastname" + i);
            steal.setSteals(i);
            result.add(steal);
            rank++;
        }
        return result;

    }
}
