package com.alejandrocastaneda.serverles_guru_challenge.application.mapper;

import java.time.LocalDateTime;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchRequestDTO;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.FootballMatchDTO;

public final class FootballMatchMapper {

    private FootballMatchMapper() {
        //empty because is a mapper
    }

    public static FootballMatch toDomain (CreateMatchRequestDTO dto) {
        return new FootballMatch(
                dto.getLocalTeam(),
                dto.getLocalTeamImageUrl(),
                dto.getAwayTeam(),
                dto.getAwayTeamImageUrl(),
                LocalDateTime.now(),
                dto.getStadium(),
                0,
                0
        );
    }

    public static FootballMatchDTO toDTO (FootballMatch footballMatch) {
        FootballMatchDTO footballMatchDTO = new FootballMatchDTO();
        footballMatchDTO.setMatchDate(footballMatch.matchDate());
        footballMatchDTO.setAwayScore(footballMatch.awayScore());
        footballMatchDTO.setAwayTeam(footballMatch.awayTeam());
        footballMatchDTO.setStadium(footballMatch.stadium());
        footballMatchDTO.setLocalScore(footballMatch.localScore());
        footballMatchDTO.setLocalTeam(footballMatch.localTeam());
        footballMatchDTO.setAwayTeamImageUrl(footballMatch.awayTeamImageUrl());
        footballMatchDTO.setLocalTeamImageUrl(footballMatch.localTeamImageUrl());

        return footballMatchDTO;
    }
}

