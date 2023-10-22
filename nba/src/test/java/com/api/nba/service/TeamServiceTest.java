package com.api.nba.service;

import com.api.nba.DTO.ConferenceTeams;
import com.api.nba.DTO.Teams;
import com.api.nba.exceptions.InvalidConferenceException;
import com.api.nba.teams.model.Conference;
import com.api.nba.teams.repository.ConferenceRepository;
import com.api.nba.teams.repository.StandingsPerSeasonRepository;
import com.api.nba.teams.repository.TeamVsTeamRepository;
import com.api.nba.teams.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    private ConferenceRepository conferenceRepository;
    @Mock
    private StandingsPerSeasonRepository seasonRepository;
    @Mock
    private TeamVsTeamRepository teamVsTeamRepository;
    @InjectMocks
    private TeamService teamService;
    private final Map<String, String> teamNames = new HashMap<>();
    private List<Conference> allTeams;
    private Map<String, List<String>> conferenceTeams;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp() {
        allTeams = initializeAllTeams();
        conferenceTeams = initializeConferences();
    }

    @Test
    void getAllTeamsShouldReturnCorrectNumberOfTeamsAndTheirNames() {
        when(conferenceRepository.findAll()).thenReturn(allTeams);

        Teams teams = teamService.getAllTeams();
        assertEquals(2, teams.teams().size());

        assertEquals("Golden State Warriors", teams.teams().get(0).getTeam());
        assertEquals("Cleveland Cavaliers", teams.teams().get(1).getTeam());
    }

    @Test
    void getAllTeamsShouldReturnCorrectJson() throws JsonProcessingException {
        when(conferenceRepository.findAll()).thenReturn(allTeams);

        Teams teams = teamService.getAllTeams();

        String actualJson = objectMapper.writeValueAsString(teams);
        String expectedJson = "{\"teams\":[{\"id\":1,\"team\":\"Golden State Warriors\",\"conference\":\"West\",\"division\":\"Pacific\"},{\"id\":2,\"team\":\"Cleveland Cavaliers\",\"conference\":\"East\",\"division\":\"Central\"}]}";

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void getAllTeamsDatabaseFailure() {
        // Simulate a database access error
        when(conferenceRepository.findAll()).thenThrow(new DataAccessException("Database is down") {});

        // Should be replaced with custom exception
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> teamService.getAllTeams()
        );

        String expectedMessage = "Could not retrieve teams from the database";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        // Verify that the findAll method on the repository was called
        verify(conferenceRepository).findAll();
    }

    @Test
    void getConferenceTeamsShouldReturnNumberAndListOfAllTeams() throws InvalidConferenceException {
        when(conferenceRepository.findWesternConferenceTeamNames()).thenReturn(conferenceTeams.get("Western Conference"));
        when(conferenceRepository.findEasternConferenceTeamNames()).thenReturn(conferenceTeams.get("Eastern Conference"));

        ConferenceTeams west = teamService.getConferenceTeams("west");
        ConferenceTeams east = teamService.getConferenceTeams("east");

        assertEquals("West", west.conference());
        assertEquals("East", east.conference());

        assertEquals(1, west.teams().size());
        assertEquals(1, east.teams().size());
    }

    @Test
    void getConferenceTeamsShouldReturnCorrectJson() throws InvalidConferenceException, JsonProcessingException {
        when(conferenceRepository.findWesternConferenceTeamNames()).thenReturn(conferenceTeams.get("Western Conference"));
        when(conferenceRepository.findEasternConferenceTeamNames()).thenReturn(conferenceTeams.get("Eastern Conference"));

        ConferenceTeams west = teamService.getConferenceTeams("west");
        ConferenceTeams east = teamService.getConferenceTeams("east");

        String expectedWest = "{\"conference\":\"West\",\"teams\":[\"Golden State Warriors\"]}";
        String actualWest = objectMapper.writeValueAsString(west);

        String expectedEast = "{\"conference\":\"East\",\"teams\":[\"Cleveland Cavaliers\"]}";
        String actualEast = objectMapper.writeValueAsString(east);

        assertEquals(expectedWest, actualWest);
        assertEquals(expectedEast, actualEast);
    }

    @Test
    void enterInvalidConferenceShouldThrowInvalidConferenceException() {
        assertThrows(InvalidConferenceException.class,
                () -> teamService.getConferenceTeams("Invalid"));
    }

    @Test
    void testInvalidConferenceExceptionShouldReturnCorrectMessage() {
        Exception exception = assertThrows(InvalidConferenceException.class,
                () -> teamService.getConferenceTeams("Invalid"));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Invalid conference: 'Invalid'. Conference can only be 'east' or 'west'";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAConferenceTeamsButDatabaseIsDown() {
        when(conferenceRepository.findWesternConferenceTeamNames()).thenThrow(new DataAccessException("Database is down") {});
        when(conferenceRepository.findEasternConferenceTeamNames()).thenThrow(new DataAccessException("Database is down") {});

        assertThrows(DataAccessException.class,
                () -> teamService.getConferenceTeams("west"));
        assertThrows(DataAccessException.class,
                () -> teamService.getConferenceTeams("east"));

        verify(conferenceRepository, times(2)).findWesternConferenceTeamNames();
        verify(conferenceRepository, times(2)).findEasternConferenceTeamNames();
    }

    private List<Conference> initializeAllTeams() {
        List<Conference> result = new ArrayList<>();

        Conference team1 = Conference.builder()
                .id(1L)
                .team("Golden State Warriors")
                .conference("West")
                .division("Pacific")
                .build();
        Conference team2 = Conference.builder()
                .id(2L)
                .team("Cleveland Cavaliers")
                .conference("East")
                .division("Central")
                .build();
        result.add(team1);
        result.add(team2);

        return result;
    }
    private Map<String, List<String>> initializeConferences() {
        Map<String, List<String>> result = new HashMap<>();
        result.put("Western Conference", Collections.singletonList("Golden State Warriors"));
        result.put("Eastern Conference", Collections.singletonList("Cleveland Cavaliers"));

        return result;
    }
}
