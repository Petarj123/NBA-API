package com.api.nba.teams;

import com.api.nba.DTO.ConferenceTeams;
import com.api.nba.DTO.DivisionTeams;
import com.api.nba.DTO.Teams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final ConferenceRepository conferenceRepository;

    public Teams getAllTeams() {
        List<Conference> teams = conferenceRepository.findAll();
        return new Teams(teams);
    }
    public ConferenceTeams getConferenceTeams(String conference){
        if (conference.equalsIgnoreCase("west")){
            return new ConferenceTeams("West", conferenceRepository.findWesternConferenceTeamNames());
        }
        else if (conference.equalsIgnoreCase("east")){
            return new ConferenceTeams("East", conferenceRepository.findEasternConferenceTeamNames());
        }
        throw new IllegalArgumentException("Conference can only be east or west");
    }
    public DivisionTeams getDivisionTeams(String division) {
        List<String> teams = switch (division.toLowerCase()) {
            case "atlantic" -> conferenceRepository.findAtlanticConferenceTeamNames();
            case "central" -> conferenceRepository.findCentralConferenceTeamNames();
            case "southeast" -> conferenceRepository.findSouthEastConferenceTeamNames();
            case "southwest" -> conferenceRepository.findSouthWestConferenceTeamNames();
            case "northeast" -> conferenceRepository.findNorthEastConferenceTeamNames();
            case "northwest" -> conferenceRepository.findNorthWestConferenceTeamNames();
            default -> throw new IllegalArgumentException("Invalid division");
        };
        return new DivisionTeams(division, teams);
    }
}
