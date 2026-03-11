package com.alejandrocastaneda.serverles_guru_challenge.application.mapper;

import java.time.LocalDateTime;

import com.alejandrocastaneda.serverles_guru_challenge.domain.entity.FootballMatch;
import com.alejandrocastaneda.serverles_guru_challenge.infrastructure.dto.CreateMatchDTO;

public final class FootballMatchMapper {

    private FootballMatchMapper() {
        //empty because is a mapper
    }

    public static FootballMatch toDomain(CreateMatchDTO dto) {
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
}

